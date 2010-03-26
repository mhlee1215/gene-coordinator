package org.ssu.ml.presentation;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTitledPanel;
import org.ssu.ml.base.NodeDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.presentation.Fig;

public class JNodeInfoPanel extends JXTitledPanel {
	
	JTable nodeTable = null;
	List<String> columnData = null;
	JScrollPane scrollPane = null;
	List<Fig> figList = null;
	
	public JNodeInfoPanel(){
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
		
		List<Fig> selectedFig = selectedInputFig;
		
		if(selectedFig == null)
			selectedFig = figList;
		else
			this.figList = selectedInputFig;
		
		System.out.println("::::::"+figList);
		
		try{
			if(columnData == null || columnData.size() == 0){
				//JOptionPane.showMessageDialog(UiGlobals.getNodeInfoFrame(),
				//	    "Eggs are not supposed to be green.", 
				//	    "Message",
				//	    JOptionPane.WARNING_MESSAGE);
			}else{
				this.setTitle("Total "+selectedFig.size()+" nodes are selected.");
				
				String[] columnStr = new String[columnData.size()];
				columnData.toArray(columnStr);
				
				System.out.println(columnData);
				
				String[][] data = new String[selectedFig.size()][columnData.size()];
				
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
				if(scrollPane != null)
					this.remove(scrollPane);
				revalidate();
				nodeTable = new JTable(data, columnStr);
				nodeTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
				nodeTable.setFillsViewportHeight(true);
				scrollPane = new JScrollPane(nodeTable);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		if(scrollPane == null){ 
			JPanel empty = new JPanel();
			empty.add(new JLabel("After annotation file loaded, then this windows will be activated."));
			scrollPane = new JScrollPane(empty);
		}
		this.add(scrollPane);
		
		
	}
	
	public void add(Fig fig){
		
	}
	
	public void removeAll(){
		
	}
	
	public void setActivate(){
		showList(null);
	}
	
	
}
