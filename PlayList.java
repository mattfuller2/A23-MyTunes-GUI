
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represent a playlist of songs using array lists.
 * 
 * @author Matt Fuller
 */
public class PlayList implements MyTunesPlayListInterface {
	private String playlistName, songTitle;
	private String playlistInfo;
	private Song playing;
	private ArrayList<Song> songList = new ArrayList<Song>();
	private int totalPlayTime = 0;

	/*
	 * Constructor: Accepts and sets a string value for the name of the PlayList. Initializes the
	 * current song playing and the songList to an empty list.
	 *
	 * @param name of PlayList
	 */
	public PlayList(String name, File file)
		{
			playlistName = name;
			setName(playlistName);
			songList = new ArrayList<Song>(getNumSongs());
			loadFromFile(file);
		}

	/**
	 * Loads songs from specified file into this PlayList. The file must have the following format:
	 * 
	 * <pre>
	 * Song 1 Title
	 * Song 1 Artist
	 * Song 1 Play time (mm:ss)
	 * Song 1 File path
	 * Song 2 Title
	 * Song 2 Artist
	 * Song 2 Play time (mm:ss)
	 * Song 2 File path
	 * etc.
	 * </pre>
	 * 
	 * @param file The file to read the songs from.
	 */
	@Override
	public void loadFromFile(File file) {
		try {
			if (file.exists()) {
				Scanner scan = new Scanner(new File("" + file));
				// String playListName = getName();
				while (scan.hasNextLine()) {
					songTitle = scan.nextLine().trim();
					String artist = scan.nextLine().trim();
					String playtime = scan.nextLine().trim();
					String songPath = scan.nextLine().trim();

					int colon = playtime.indexOf(':');
					int minutes = Integer.parseInt(playtime.substring(0, colon));
					int seconds = Integer.parseInt(playtime.substring(colon + 1));
					totalPlayTime = (minutes * 60) + seconds;

					Song song = new Song(songTitle, artist, totalPlayTime, songPath);
					songList.add(song);
					// System.out.println("Song: " + song);
				}
				scan.close();
				// System.out.println("Load Songs: " + songList);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to load playlist. " + e.getMessage());
		}
	}

	/**
	 * The parameter sets the name of this playlist.
	 * 
	 * @param name of song
	 */
	@Override
	public void setName(String playlistName) {
		this.playlistName = playlistName;
	}

	/**
	 * Returns the name of this playlist..
	 * 
	 * @return name of song
	 */
	@Override
	public String getName() {
		return playlistName;
	}

	/**
	 * Returns the the song playing.
	 * 
	 * @return playing
	 */
	@Override
	public Song getPlaying() {
		return playing;
	}

	/**
	 * Creates a Song object and adds it to the list.
	 * 
	 * @param songName name of the Song object created
	 */
	@Override
	public void addSong(Song songName) {
		songList.add(songName);
	}

	/**
	 * Returns a Song object based on the given parameter and returns null if parameter is out of
	 * range.
	 * 
	 * @param index gets Song object at index
	 * @return songList.get(s) a song in the list
	 */
	@Override
	public Song getSong(int index) {
		int s = index;
		if (s >= 0 && s < songList.size()) {
			return songList.get(s);
		}
		return null;
	}

	/**
	 * Returns null if parameter index is out of range and returns songList.remove(i) if index is in
	 * bounds. Removes song from list.
	 * 
	 * @param index removes a Song object from list at index
	 * @return songList.remove(i) removes the Song from the list
	 */
	@Override
	public Song removeSong(int index) {
		int i = index;
		for (i = index; index >= 0 && index < songList.size();) {
			return songList.remove(i);
		}
		if (i < 0 || i >= songList.size()) {
			return null;
		}
		songList.remove(i);
		return songList.get(i);
	}

	/**
	 * Returns the size of the ArrayList.
	 * 
	 * @return songList.size() size of list
	 */
	@Override
	public int getNumSongs() {
		return songList.size();
	}

	/**
	 * Converts minutes and seconds into total seconds then returns the total play time of all songs
	 * in the playList.
	 * 
	 * @return totalPlayTime total play time in seconds of PlayList.
	 */
	@Override
	public int getTotalPlayTime() {
		totalPlayTime = 0;
		if (songList.size() == 0) {
			return 0;
		} else {
			for (Song s : songList) {
				totalPlayTime += s.getPlayTime();
			}
		}
		System.out.println("Total play time1: " + totalPlayTime);
		return totalPlayTime;
	}

	/**
	 * Returns the song in the arraylist using the given parameter and returns no if the parameter
	 * is out of range.
	 * 
	 * @param id plays Song object at index
	 * @return playing play's song
	 */
	@Override
	public void playSong(int index) {
		playing = getSong(index);
	}

	/**
	 * Added for P5. Plays the given song (only if the song list contains the song). If it doesn't,
	 * then it does nothing.
	 * 
	 * @param the song to play.
	 */
	@Override
	public void playSong(Song song) {
		if (songList.contains(song)) {
			playing = song;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see MyTunesPlayListInterface#stop()
	 */
	@Override
	public void stop() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see MyTunesPlayListInterface#getSongArray()
	 */
	@Override
	public Song[] getSongArray() {
		Song[] copy = new Song[songList.size()];
		for (int i = 0; i < songList.size(); i++) {
			copy[i] = songList.get(i);
		}
		return copy;
	}

	/**
	 * Returns a String that informs the user of the playlist's contents.
	 * 
	 * @return getInfo Playlist's details
	 */
	public String getInfo() {
		if (getSongList().size() > 0) {
			playlistInfo = playlistName + " (" + songList.size() + " songs)\n" + " - " + getTotalPlayTime();
		} else
			playlistInfo = "No Playlist Loaded 0 Songs 0 Minutes";
		return playlistInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getSongList().size() > 0) {
			return playlistName + " (" + songList.size() + " songs)" + " - " + getTotalPlayTime()
			        + " seconds";
		}
		return "Playlist";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see MyTunesPlayListInterface#moveUp(int)
	 */
	@Override
	public int moveUp(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see MyTunesPlayListInterface#moveDown(int)
	 */
	@Override
	public int moveDown(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Calculates and returns the dimensions of the 2D array
	 * 
	 * @return songArray Dimensions of 2D array
	 */
	@Override
	public Song[][] getSongSquare() {
		double arraySize = Math.ceil(Math.sqrt(getNumSongs()));
		int rowSize = (int) arraySize;
		int colSize = (int) arraySize;
		int i = 0;
		Song[][] songArray = new Song[rowSize][colSize];
		for (int row = 0; row < songArray.length; row++) {
			for (int col = 0; col < songArray[0].length; col++) {
				songArray[row][col] = songList.get(i);
				if (i < songList.size() - 1)
					i++;
				else
					i = 0;
			}
		}
		return songArray;
	}

	/**
	 * Returns the ArrayList of Songs.
	 * 
	 * @return songList
	 */
	public ArrayList<Song> getSongList() {
		return songList;
	}

}
