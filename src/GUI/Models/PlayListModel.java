package GUI.Models;

import BE.PlayList;
import BE.Song;
import BLL.PlayListManager;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayListModel {
    private ObservableList<PlayList> playListsInList;
    private PlayListManager playListManager;

    /**
     * returns the ObservableList playListsInList
     * @return
     */
    public ObservableList<PlayList> getObservablePlayLists() {
        return playListsInList;
    }

    /**
     * adds all playlists to playListInList
     * @throws Exception
     */
    public PlayListModel() throws Exception {
        playListManager = new PlayListManager();
        playListsInList = FXCollections.observableArrayList();
        playListsInList.addAll(playListManager.getAllPlayLists());

    }

}
