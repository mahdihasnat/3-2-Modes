package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ChunkReader
{
	File file;
	long chunkSize;

	public ChunkReader(File file, long chunkSize)
	{
		this.file = file;
		this.chunkSize = chunkSize;
	}

	public byte[] readChunk(long chunkNumber) throws IOException
	{
		byte[] chunk = new byte[(int) chunkSize];
		FileInputStream fis = new FileInputStream(file);
		fis.skip(chunkNumber * chunkSize);
		System.out.println("Read chunk " + fis.read(chunk));
		fis.close();
		return chunk;
	}

	public static void main(String [] args) {
		File f = new File("readme.md");
		long sz = f.length();
		long chunkSize = 5;
		ChunkReader cr = new ChunkReader(f, chunkSize);
		try {
			for(long i=0;i<sz;i+=chunkSize) {
				byte[] chunk = cr.readChunk(i/chunkSize);
				System.out.println(new String(chunk));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}