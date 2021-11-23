package server;

import server.file.FileManager;
import server.file.UploadBuffer;
import server.file.buffer.FileBuffer;
import server.file.buffer.RequestFileBuffer;
import server.file.buffer.UserFileBuffer;
import server.filerequest.FileRequest;
import server.filerequest.FileRequestHandler;
import util.log.LogLevel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;


    ClientThread(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getOut() {
        return out;
    }

    private void fileUploadOperation() {
        int fileId = -1;
        try {
            fileId = in.readInt();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        if (fileId == -1) return;
        FileBuffer fileBuffer = UploadBuffer.getInstance().getRunningFileBuffer(fileId);
        try {

            while (true) {
                String operation = in.readUTF();
                //System.out.println("operation:"+operation);
                if (operation.equals("completeupload")) {
                    if (fileBuffer.checkIntegrity()) {
                        out.writeUTF("ack");
                        out.flush();
                        if (fileBuffer.writeFile()) {
                            UploadBuffer.getInstance().abortCurrentOperation(fileId);
                            FileManager.getInstance().refreshFiles(fileBuffer.getOwner());
                        } else {
                            out.writeUTF("print");
                            out.writeUTF("Uploaded file was unable to saved| #" + fileId);
                            out.flush();
                        }
                    } else {
                        out.writeUTF("nack");
                        out.flush();
                    }
                    break;
                } else if (operation.equals("uploadchunk")) {
                    int length = in.readInt();
                    //System.out.println("length:" + length);
                    byte[] datas = new byte[length];
                    in.read(datas);
                    fileBuffer.addData(datas);
                    //Thread.sleep(10000);
                    out.writeUTF("ack");
                    out.flush();
                } else if (operation.equals("abort")) {
                    break;
                } else {
                    System.out.println("FileUploadOperation not defined " + operation);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        UploadBuffer.getInstance().releaseBuffer(fileBuffer.getFileLength());
    }

    private void loginOperation() {
        String sid = null;
        StudentDirectory studentDirectory = StudentDirectory.getInstance();
        FileManager fileManager = FileManager.getInstance();
        FileRequestHandler fileRequestHandler = FileRequestHandler.getInstance();
        UploadBuffer uploadBuffer = UploadBuffer.getInstance();
        try {

            sid = in.readUTF();

            if (studentDirectory.addNewStudent(sid, this)) {
                out.writeUTF("success");
                out.flush();
                while (true) {
                    String operation = in.readUTF();

                    synchronized (out) {

                        if (operation.equals("showstudents")) {
                            out.writeUTF("allstudents");
                            out.writeUTF(studentDirectory.getAllStudent());
                        } else if (operation.equals("showmyfiles")) {
                            out.writeUTF("myfiles");
                            out.writeUTF(fileManager.getStudentFiles(sid));
                        } else if (operation.equals("showallfiles")) {
                            out.writeUTF("allfiles");
                            out.writeUTF(fileManager.getAllPublicFiles());
                        } else if (operation.equals("showfiles")) {
                            String sid_other = in.readUTF();
                            out.writeUTF("allfiles");
                            out.writeUTF(fileManager.getPublicFiles(sid_other));
                        } else if (operation.equals("requestfile")) {
                            String msg = in.readUTF();
                            FileRequest fileRequest = fileRequestHandler.addNewRequest(sid, msg);
                            Thread broadcast = new BroadcastThread(fileRequest.toString());
                            broadcast.start();
                        } else if (operation.equals("upload")) {
                            /// upload visi filename filesize
                            String visibility = in.readUTF();
                            String fileName = in.readUTF();
                            long fileSize = in.readLong();
                            FileBuffer fileBuffer = null;
                            if (visibility.equals("private") || visibility.equals("public")) {
                                fileBuffer = new UserFileBuffer(sid, visibility, fileName, fileSize);
                            } else {
                                try {
                                    int requestId = Integer.parseInt(visibility);
                                    FileRequest fileRequest = FileRequestHandler.getInstance().getFileRequest(requestId);
                                    if (fileRequest == null)
                                        throw new Exception("Request not found");
                                    fileBuffer = new RequestFileBuffer(sid, fileName, fileSize, fileRequest);
                                } catch (NumberFormatException e) {
                                    out.writeUTF("print");
                                    out.writeUTF("RequestId is not valid Number");
                                    out.flush();
                                    out.writeUTF("fileiderror");
                                    out.writeUTF(fileName);

                                } catch (Exception e) {
                                    out.writeUTF("print");
                                    out.writeUTF("Request not found!");
                                    out.flush();
                                    out.writeUTF("fileiderror");
                                    out.writeUTF(fileName);
                                    out.flush();
                                    //e.printStackTrace();
                                }

                            }
                            if (fileBuffer != null) {
                                int fileId = uploadBuffer.addOperation(fileBuffer);
                                out.writeUTF("fileid");
                                out.writeUTF(fileName);
                                out.writeInt(fileId);
                            }

                        } else if (operation.equals("uploadabort")) {
                            int fileId = in.readInt();
                            FileBuffer fileBuffer = uploadBuffer.getRunningFileBuffer(fileId);
                            uploadBuffer.releaseBuffer(fileBuffer.getFileLength());
                            uploadBuffer.abortCurrentOperation(fileId);
                            ServerLogger.getLogger().logMessage(LogLevel.INFO, "Upload Canceled #" + fileId);
                        } else if (operation.equals("download")) {
                            String fileOwner = in.readUTF();
                            String visibility = in.readUTF();
                            String fileName = in.readUTF();
                            ServerLogger.getLogger().logMessage(LogLevel.DEBUG, "download.fileOwner" + fileOwner);
                            ServerLogger.getLogger().logMessage(LogLevel.DEBUG, "download.visibility" + visibility);
                            ServerLogger.getLogger().logMessage(LogLevel.DEBUG, "download.fileName" + fileName);

                            if (visibility.equals("private") && !fileOwner.equals(sid)) {

                                out.writeUTF("print");
                                out.writeUTF("Invalid access to others private files");

                            } else {
                                int totalMatch = fileManager.totalMatchFile(fileOwner, visibility, fileName);
                                if (totalMatch <= 0) {
                                    out.writeUTF("print");
                                    out.writeUTF("No file found!");
                                } else if (totalMatch > 1) {
                                    out.writeUTF("print");
                                    out.writeUTF("Multiple Files found with prefix");
                                } else {
                                    File file = fileManager.getFile(fileOwner, visibility, fileName);
                                    int fileId = uploadBuffer.getNewFileId();
                                    int chunkSize = Settings.getInstance().getMAX_CHUNK_SIZE();
                                    Thread fileDownloaderThread = new FileDownloaderThread(file, chunkSize, fileId, out);
                                    fileDownloaderThread.start();
                                    out.writeUTF("print");
                                    out.writeUTF("Download will start shortly");
                                }
                            }


                        } else {
                            out.writeUTF("print");
                            out.writeUTF("Incorrect operation :" + operation);

                            System.out.println("opeation: " + operation + " not defined for " + sid);
                        }
                        out.flush();
                    }
                }
            } else {
                out.writeUTF("denay");
                out.flush();
                System.out.println("Refused multiple connection request with student id: " + sid);
            }

        } catch (IOException e) {

            if (sid != null) {
                studentDirectory.logoutStudent(sid);
            }

            //e.printStackTrace();
        }
    }

    public void run() {
        try {
            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mode = null;
        try {
            mode = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mode.equals("fileupload"))
            fileUploadOperation();
        else
            loginOperation();

        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
