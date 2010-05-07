package org.ssu.ml.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTitledPanel;
import org.ssu.ml.base.NodeDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.Editor;
import org.tigris.gef.presentation.Fig;

public class JNodeInfoPanel extends JXTitledPanel {
	
	JPanel main = null;
	JTable nodeTable = null;
	private JTextField filterText;
	private JComboBox filterColumn;
	private TableRowSorter<JNodeInfoTableModel> sorter;
	List<String> columnData = null;
	JScrollPane scrollPane = null;
	List<Fig> figList = null;
	
	String[] column = null;
	Object[][] data = null;
	
	/**
	 * @return the column
	 */
	public String[] getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(String[] column) {
		this.column = column;
	}
	/**
	 * @return the data
	 */
	public Object[][] getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}
	/**
	 * @return the figList
	 */
	public List<Fig> getFigList() {
		return figList;
	}
	/**
	 * @param figList the figList to set
	 */
	public void setFigList(List<Fig> figList) {
		this.figList = figList;
	}
	public JNodeInfoPanel(){
		//this.setLayout(new MigLayout());
		//setLayout(new FlowLayout);
		//this.setTitle("Node(s) Info.");
	}
	/**
	 * @return the columnData
	 */
	public List<String> getColumnData() {
		return columnData;
	}

	/**
	 * @param columnData the columnData to set
	 */
	public void setColumnData(List<String> columnData) {
		this.columnData = columnData;
	}

	public void showList(List<Fig> selectedInputFig){
		scrollPane = null;
		
		if(main != null){
			main.removeAll();
			this.remove(main);
		}
		
		List<Fig> selectedFig = selectedInputFig;
		
		if(selectedFig == null)
			selectedFig = figList;
		else
			this.figList = selectedInputFig;
		
		System.out.println("::::::"+figList.size());
		
		try{
			if(column == null && (columnData == null || columnData.size() == 0)){
				//JOptionPane.showMessageDialog(UiGlobals.getNodeInfoFrame(),
				//	    "Eggs are not supposed to be green.", 
				//	    "Message",
				//	    JOptionPane.WARNING_MESSAGE);
			}else{
				if(selectedFig != null)
					this.setTitle("Total "+selectedFig.size()+" nodes are selected.");
				
				if(column == null){
					column = new String[columnData.size()];
					columnData.toArray(column);
				}
				
				
				//if(data == null){
					data = new Object[selectedFig.size()][columnData.size()];
					
					HashMap<String, HashMap<Integer, String>> annotationContent = UiGlobals.getAnnotationContent();
					
					int count = 0;
					for(Fig fig : selectedFig){
						if( fig.getOwner() instanceof NodeDescriptor){
							NodeDescriptor des = (NodeDescriptor)fig.getOwner();
							HashMap<Integer, String> property = annotationContent.get(des.getName());
							
							Set<Integer> keys = property.keySet();
							for(int key = 0 ; key < columnData.size() ; key++){
								
								data[count][key] = property.get(key);
							}
						}
						count++;
					}
				
				//}
				
				if(scrollPane != null)
					this.remove(scrollPane);
				revalidate();
				
				System.out.println("tableCreated");
				JNodeInfoTableModel model = new JNodeInfoTableModel(column, data);
				sorter = new TableRowSorter<JNodeInfoTableModel>(model);
				nodeTable = new JTable(model);
				nodeTable.setRowSorter(sorter);
				nodeTable.setEnabled(true);
				//nodeTable.setAutoCreateRowSorter(true);
				nodeTable.setPreferredScrollableViewportSize(new Dimension(2000, 2000));
				nodeTable.setFillsViewportHeight(true);
				nodeTable.getSelectionModel().addListSelectionListener(new RowListener());
				scrollPane = new JScrollPane(nodeTable);
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		MigLayout layout = new MigLayout(new LC().fillX().insets("0 0 0 0"),
				 new AC().align("left").gap("rel").grow(1f).fill(),
				 new AC().gap("0"));
		main = new JPanel();
		main.setLayout(layout);
		
		if(scrollPane == null){ 
			MigLayout layout1 = new MigLayout(new LC().fillX().fillY().insets("0 0 0 0"),
					 new AC().align("center").gap("rel").grow(1f).fill(),
					 new AC().gap("0"));
			JPanel empty = new JPanel();
			empty.setLayout(layout1);
			empty.add(new JLabel("After annotation file is loaded, then this windows will be activated."));
			scrollPane = new JScrollPane(empty);
		}else{
			JLabel filterLabel = new JLabel("Filter column: ");
			main.add(filterLabel);
			filterColumn = new JComboBox();
			for(String columSr : column)
				filterColumn.addItem(columSr);
			main.add(filterColumn, "wrap");
			JLabel filterTextLabel = new JLabel("Filter value: ");
			main.add(filterTextLabel);
			filterText = new JTextField();
			
			filterText.getDocument().addDocumentListener(
	                new DocumentListener() {
	                    public void changedUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void insertUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void removeUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                });
			main.add(filterText, "wrap");
		}
		
		CC cc = new CC();
		main.add(scrollPane, cc.wrap().spanX(2));
		
		this.add(main);
		
		
	}
	
	private void newFilter() {
        RowFilter<JNodeInfoTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), filterColumn.getSelectedIndex());
        } catch (java.util.regex.PatternSyntaxException e) {
        	e.printStackTrace();
            return;
        }
        sorter.setRowFilter(rf);
    }
	
	public void add(Fig fig){
		
	}
	
	public void removeAll(){
		
	}
	
	public void setActivate(){
		showList(null);
	}
	
	public static void main(String[] argv){
		JFrame frame = new JFrame();
		JNodeInfoPanel panel = new JNodeInfoPanel();
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		Object[][] data = {
		{"Mary", "Campione",
		"Snowboarding", new Integer(5), new Boolean(false)},
		{"Alison", "Huml",
		"Rowing", new Integer(3), new Boolean(true)},
		{"Kathy", "Walrath",
		"Knitting", new Integer(2), new Boolean(false)},
		{"Sharon", "Zakhour",
		"Speed reading", new Integer(20), new Boolean(true)},
		{"Philip", "Milne",
		"Pool", new Integer(10), new Boolean(false)},
		};
		
		panel.setColumn(columnNames);
		panel.setData(data);
		
		panel.setSize(500, 300);
		panel.showList(null);
		frame.add(panel);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}
	
	private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            outputSelection();
        }
    }
	
	private void outputSelection() {
		for(FigCustomNode node : UiGlobals.getInfoMarkedNode())
			node.resetbyInfoPanel();
		UiGlobals.getInfoMarkedNode().clear();
        for (int c : nodeTable.getSelectedRows()) {
            System.out.println(nodeTable.getModel().getValueAt(c, 0));
            Fig selectedNode = UiGlobals.getNodeHash().get(nodeTable.getModel().getValueAt(c, 0));
            FigCustomNode selectedNodeCustom = (FigCustomNode)selectedNode;
            selectedNodeCustom.markByInfoPanel();
            UiGlobals.getInfoMarkedNode().add(selectedNodeCustom);
            Editor editor = UiGlobals.curEditor();
            editor.getLayerManager().getActiveLayer().reorder(selectedNodeCustom, CmdReorder.BRING_TO_FRONT);
            editor.damaged(selectedNodeCustom);
        }
        
	}
}




