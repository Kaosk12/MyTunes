package GUI.Util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.concurrent.TimeUnit;

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


    /**
     * converts time to hours, minutes and seconds
     * @param totalTime The time to convert in seconds.
     */
    private void convertTime(int totalTime){

        long hour = TimeUnit.MINUTES.toHours(totalTime);

        long minute  = TimeUnit.SECONDS.toMinutes(totalTime) - (TimeUnit.MINUTES.toHours(totalTime) * 60);

        long second = totalTime -(TimeUnit.SECONDS.toMinutes(totalTime)*60);

        String convertedTime = "Hours " + hour + " Mins " + minute + " Sec " + second;
    }

}