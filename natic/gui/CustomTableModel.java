package natic.gui;

import java.util.ArrayList;
import javax.swing.table.*;

public class CustomTableModel extends AbstractTableModel {
    private String[] tableHeaders;
    private ArrayList<ArrayList<Object>> tableData;
    private static final long serialVersionUID = 1L;

    public CustomTableModel(String[] tableHeaders, ArrayList<ArrayList<Object>> tableData) {
        this.tableHeaders = tableHeaders;
        this.tableData = tableData;
    }

    @Override
    public int getRowCount() {
        return this.tableData.size();
    }

    @Override
    public int getColumnCount() {
        return this.tableHeaders.length;
    }

    @Override
    public String getColumnName(int col) {
        return this.tableHeaders[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return this.tableData.get(row).get(col);
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
