package server;

public class Settings {
	// sizes are in byte
	private int MAX_BUFFER_SIZE;
	private int MIN_CHUNK_SIZE;
	private int MAX_CHUNK_SIZE;
	private int PORT ;
	private String path;

	private Settings() 
	{
		MAX_BUFFER_SIZE = 1024*1024*1024;
		MIN_CHUNK_SIZE = 1;
		MAX_CHUNK_SIZE = 1024;
		PORT = 6666;
		path = "data\\";
	}

	public String getPath() {
		return path;
	}

	public int getMAX_BUFFER_SIZE() {
		return MAX_BUFFER_SIZE;
	}

	public int getPORT() {
		return PORT;
	}

	private static Settings instance;
	public static Settings getInstance()
	{
		if(instance == null)
		{
			instance = new Settings();
		}
		return instance;
	}
}
