import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReplaysTable extends JTable {
    public ReplaysTable(TableModel dm) {
        super(dm);
        setRowSorter(new TableRowSorter<>(dm));
        getRowSorter().toggleSortOrder(1);
        getRowSorter().toggleSortOrder(1);
        setRowSelectionAllowed(true);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getColumn("Cloud").setMaxWidth(45);
        getColumn("Cloud").setMinWidth(45);
        getColumn("Date").setMinWidth(100);
        getColumn("Date").setMaxWidth(100);
        getColumn("").setMaxWidth(0);

        getColumn("Date").setCellRenderer(new DefaultTableCellRenderer(){
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/YYYY");

            @Override
            protected void setValue(Object value) {
                if(value instanceof Date)
                {
                    super.setText(dateFormat.format(value));
                }
                else
                {
                    super.setText("NA");
                }

            }
        });
    }
}
