package soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow.Utils;

import javafx.scene.control.TableRow;
import soulintec.com.tmsclient.Entities.OperationType;
import soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow.TransactionsModel;

public class EnhancedTableRow extends TableRow<TransactionsModel.TableObject> {

    String style = "";

    public EnhancedTableRow() {
        super();
    }

    @Override
    protected void updateItem(TransactionsModel.TableObject item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || item.getOperationTypeColumn() == null) {
            style = "";
        } else if (item.getOperationTypeColumn().equals(OperationType.In)) {
            style = ("-fx-background-color: green; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
        } else if (item.getOperationTypeColumn().equals(OperationType.Out)) {
            style = ("-fx-background-color: orangered; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
        } else {
            style = "";
        }
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
        if (selected) {
            setStyle("-fx-background-color: DARKCYAN; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
        } else {
            setStyle(style);
        }
    }
}
