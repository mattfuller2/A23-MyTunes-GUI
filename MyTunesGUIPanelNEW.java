import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 *
 * @author mdfulls
 */
@SuppressWarnings("serial")
public class MyTunesGUIPanelNEW extends JPanel {
	private PlayList playlist;
	private JPanel gridPanel;
	private JLabel playlistNameLabel, playlistInfoLabel, songPlayingLabel;
	private JList<Song> songList;
	private JButton moveUpButton, moveDownButton, addSongButton, removeSongButton, playPrevButton,
	        playNextButton, playButton;
	private JButton[][] songSquareButtons;
	private Song songPlaying;
	private Song[] songArray;
	private Song[][] songSquare;
	private String playlistName, songTitle;
	private File filepath;

	public MyTunesGUIPanelNEW()
		{
			// Entire frame will have a BorderLayout
			setLayout(new BorderLayout());
			setupPlaylist();
			CreateAndShowLayoutPanel();
		}

	private void setupPlaylist() {
		playlistName = "My Playlist";
		filepath = new File("playlist.txt");
		playlist = new PlayList("playlist.txt", filepath);

		// Updates the JList<Song> songList object
		songList = new JList<Song>(playlist.getSongArray());
		songList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		songList.setSelectedIndex(0);
	}

	/**
	 * Creates a JPanel containing the controlButtons used to navigate through the list of songs.
	 */
	private void CreateAndShowLayoutPanel() {
		configureRightPanel();
		configureLeftPanel();
		configureTopPanel();
		configureBottomPanel();
	}

	// Method to create the leftPanel in the main panel
	private void configureLeftPanel() {
		// Panel holds the moveUp/Down buttons and the scrollPanel of songs.
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		// Panel holds the moveUp/Down buttons and scrollPanel.
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));

		// Creates a move up button and move down button.
		JPanel upDownPanel = new JPanel();
		upDownPanel.setLayout(new BoxLayout(upDownPanel, BoxLayout.Y_AXIS));
		moveUpButton = new JButton("^");
		moveDownButton = new JButton("v");

		JScrollPane scrollPanel = new JScrollPane(songList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Panel holds the add and remove song buttons.
		JPanel addRemoveSongPanel = new JPanel();
		addRemoveSongPanel.setLayout(new BoxLayout(addRemoveSongPanel, BoxLayout.X_AXIS));
		addSongButton = new JButton("Add Song");
		removeSongButton = new JButton("Remove Song");

		add(leftPanel, BorderLayout.CENTER);
		leftPanel.add(controlPanel);
		leftPanel.add(addRemoveSongPanel);
		addRemoveSongPanel.add(addSongButton);
		addRemoveSongPanel.add(removeSongButton);
		controlPanel.add(upDownPanel);
		controlPanel.add(scrollPanel);
		upDownPanel.add(moveUpButton);
		upDownPanel.add(moveDownButton);
	}

	// Method to create the rightPanel in the main panel.
	private void configureRightPanel() {
		if (gridPanel != null)
			this.remove(gridPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		gridPanel = new JPanel();
		songSquare = playlist.getSongSquare();

		gridPanel.setLayout(new GridLayout(songSquare.length, songSquare.length));

		songSquareButtons = new JButton[songSquare.length][songSquare.length];

		for (int i = 0; i < songSquare.length; i++) {
			for (int j = 0; j < songSquare.length; j++) {
				songTitle = songSquare[i][j].getTitle();
				songSquareButtons[i][j] = new JButton(songTitle);
				// songSquareButtons[i][j].addActionListener(songSquareListener);
				gridPanel.add(songSquareButtons[i][j]);
			}
		}
		add(rightPanel, BorderLayout.EAST);
		rightPanel.add(gridPanel);
	}

	// Method to create the topPanel in the main panel.
	private void configureTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		playlistNameLabel = new JLabel(playlistName);
		playlistNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
		playlistNameLabel.setAlignmentX(CENTER_ALIGNMENT);

		playlistInfoLabel = new JLabel(playlist.getInfo());
		playlistInfoLabel.setAlignmentX(CENTER_ALIGNMENT);

		add(topPanel, BorderLayout.NORTH);
		topPanel.add(playlistNameLabel);
		topPanel.add(playlistInfoLabel);
	}

	// Method to create the bottomPanel in the main panel.
	private void configureBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createTitledBorder("Now Playing"));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

		// Creates a sub-panel for prevButton, playButton, and playNextButton.
		JPanel playSongPanel = new JPanel();
		playSongPanel.setLayout(new BoxLayout(playSongPanel, BoxLayout.X_AXIS));
		playSongPanel.setAlignmentX(CENTER_ALIGNMENT);
		playPrevButton = new JButton("<<");
		playButton = new JButton("Play");
		playNextButton = new JButton(">>");

		// Creates a sub-panel for the title and artist of the current song playing.
		JPanel currentSongInfoPanel = new JPanel();
		currentSongInfoPanel.setLayout(new BoxLayout(currentSongInfoPanel, BoxLayout.X_AXIS));
		currentSongInfoPanel.setAlignmentX(CENTER_ALIGNMENT);
		songPlaying = playlist.getPlaying();
		songPlayingLabel = new JLabel("" + songPlaying);

		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(playSongPanel);
		bottomPanel.add(currentSongInfoPanel);
		playSongPanel.add(playPrevButton);
		playSongPanel.add(playButton);
		playSongPanel.add(playNextButton);
		currentSongInfoPanel.add(songPlayingLabel);
	}
}
