package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContinuousChunkReader {
	FileInputStream fis;

	public ContinuousChunkReader(File file) throws FileNotFoundException {
		this.fis = new FileInputStream(file);
	}

	public int readNextChunk(byte [] data) throws IOException {
		int read = fis.read(data);
		// 0 - if data.length == 0
		// -1- if no more data
		// or length of data read
		return read;
	}

	public static void main(String[] args) {
		File f = new File("readme.md");
		try {
			ContinuousChunkReader continuousChunkReader  = new ContinuousChunkReader(f);
			int tot = (int) (f.length()/5) + 2;
			while (tot-- > 0)
			{
				byte [] x = new byte[5];
				System.out.println(continuousChunkReader.readNextChunk(x));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
