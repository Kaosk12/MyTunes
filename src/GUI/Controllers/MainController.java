package GUI.Controllers;

import BE.PlayList;
import BE.Song;
import GUI.Models.MainModel;
import GUI.Models.PlayListModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {

    //Song list variables
    public TextField txtSongSearch;
    public TableView<Song> lstSongs;
    public TableColumn<Song, String> titleColum;
    public TableColumn<Song, String> artistColum;
    public TableColumn<Song, String> genreColum;
    public TableColumn<Song, Integer> timeColum;

    //PlayList variables
    public TableColumn<PlayList, String> clmPlayListName;
    public TableColumn<PlayList, Integer> clmPlayListSongs;
    public TableColumn<PlayList, String> clmPlayListTime;
    public TableView<PlayList> tbvPlayLists;
    public Button btnNewPlayList;
    public Button btnEditPlayList;
    public Button btnDeletePlayList;


    private MainModel songModel;
    private PlayListModel playlistModel;

    public MainController(){
        try {
            songModel = new MainModel();
            playlistModel = new PlayListModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstSongs.setItems(songModel.getObservableSongs());

        titleColum.setCellValueFactory(new PropertyValueFactory<>("Title"));
        artistColum.setCellValueFactory(new PropertyValueFactory<>("Authour"));
        genreColum.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        timeColum.setCellValueFactory(new PropertyValueFactory<>("Time"));


        /**
         * sets the Cell value for the 3 cells in tableView for PlayList.
         * Its uses the name of the variable in the PlayList object from BE.
         */
        tbvPlayLists.setItems(playlistModel.getObservablePlayLists());
        clmPlayListName.setCellValueFactory(new PropertyValueFactory<>("Title"));
        clmPlayListSongs.setCellValueFactory(new PropertyValueFactory<>("SongAmount"));
        clmPlayListTime.setCellValueFactory(new PropertyValueFactory<>("Time"));


    }

    /**
     * converts time to hours, minutes and seconds
     * @param timeInSeconds
     */
    private void convertTime(int timeInSeconds){
        int totalTime = timeInSeconds;
        long hour = TimeUnit.MINUTES.toHours(totalTime);

        long minute  = TimeUnit.SECONDS.toMinutes(totalTime) - (TimeUnit.MINUTES.toHours(totalTime) * 60);

        long second = totalTime -(TimeUnit.SECONDS.toMinutes(totalTime)*60);

        String convertedTime = "Hours " + hour + " Mins " + minute + " Sec " + second;
    }
}
