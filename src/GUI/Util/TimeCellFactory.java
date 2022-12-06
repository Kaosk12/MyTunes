package GUI.Util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TimeCellFactory<T> implements Callback<TableColumn<T, Integer>, TableCell<T, Integer>> {

    @Override
    public TableCell<T, Integer> call(TableColumn<T, Integer> col) {
        return new TableCell<T, Integer>() {

            @Override
            protected void updateItem(Integer time, boolean empty) {
                super.updateItem(time, empty);
                if ((time == null) || empty) {
                    setText(null);
                    return;
                }
                int m = time/60;
                int s = time%60;
                String mins = String.format("%02d", m);
                String secs = String.format("%02d", s);

                setText(mins+":"+secs);
            }
        };
    }
}