package GUI.Controllers;

import BE.Song;
import GUI.Models.MainModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //Song list variables
    public TextField txtSongSearch;
    public TableView<Song> lstSongs;
    public TableColumn<Song, String> titleColum;
    public TableColumn<Song, String> artistColum;
    public TableColumn<Song, String> genreColum;
    public TableColumn<Song, Integer> timeColum;

    //PlayList variables
    public TableColumn clmPlayListName;
    public TableColumn clmPlayListSongs;
    public TableColumn clmPlayListTime;
    public TableView tbvPlayLists;
    public Button btnNewPlayList;
    public Button btnEditPlayList;
    public Button btnDeletePlayList;


    private MainModel songModel;

    public MainController(){
        try {
            songModel = new MainModel();
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


        //PlayLists
        tbvPlayLists.setItems(songModel.getObservablePlayLists());
        clmPlayListName.setCellValueFactory(new PropertyValueFactory<>("Title"));
        //clmPlayListSongs.setCellValueFactory(new PropertyValueFactory<>("Songs"));
        //clmPlayListTime.setCellValueFactory(new PropertyValueFactory<>("Time"));




    }
}
