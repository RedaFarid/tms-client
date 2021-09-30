package soulintec.com.tmsclient.Graphics.Windows.TanksWindow.Utils;


import javafx.scene.control.TableRow;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.TanksModel;

public class EnhancedTableRow extends TableRow<TanksModel.TableObject> {

//    String style = "";
//
//    public EnhancedTableRow() {
//        super();
//    }
//
//    @Override
//    protected void updateItem(TanksModel.TableObject item, boolean empty) {
//        super.updateItem(item, empty);
//
//        if (item == null || item.getStatus() == null || item.getStatus() == null || item.getPermission() == null) {
//            style = "";
//        } else if (item.getStatus().equals(TankStatus.Good) && item.getPermission().equals(Permissions.PERMITTED)) {
//            style = ("-fx-background-color: green; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
//        } else if (!item.getStatus().equals(TankStatus.Good) || !item.getPermission().equals(Permissions.PERMITTED)) {
//            style = ("-fx-background-color: orangered; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
//        } else {
//            style = "";
//        }
//    }
//
//    @Override
//    public void updateSelected(boolean selected) {
//        super.updateSelected(selected);
//        if (selected) {
//            setStyle("-fx-background-color: DARKCYAN; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
//        } else {
//            setStyle(style);
//        }
//    }
}
