package swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class Phonebook {

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		
		jFrame.setTitle("Phone Book");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(new Dimension(700, 350));
		jFrame.setLocationRelativeTo(null);
		
		List<Contact> list = new ArrayList<>();
		
		for (int i = 0; i < 50; i++) {
			list.add(new Contact(i,"name" + i, "dept" + i, "phone" + i, "cell" + i));
		}
		
		TableModel tableModel = TableModelCreator.createTableModel(Contact.class, list);
		
		JTable table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);

		JScrollPane pane = new JScrollPane(table);
		
		jFrame.add(pane);
		
		jFrame.setVisible(true);

	}

}

class Contact {
	private long id;
	private String name;
	private String dept;
	private String phone;
	private String cellPhone;
	
	public Contact() {
		
	}
	
	public Contact(long id, String name, String dept, String phone, String cellPhone) {
		super();
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.phone = phone;
		this.cellPhone = cellPhone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
}

class TableModelCreator {

  public static <T> TableModel createTableModel(Class<T> beanClass, List<T> list) {
      try {
    	  
          BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
          List<String> columns = new ArrayList<>();
          List<Method> getters = new ArrayList<>();

          
          for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
              String name = pd.getName();
              if (name.equals("class")) {
                  continue;
              }
              name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
              String[] s = name.split("(?=\\p{Upper})");
              String displayName = "";
              for (String s1 : s) {
                  displayName += s1 + " ";
              }

              columns.add(displayName);
              Method m = pd.getReadMethod();
              getters.add(m);
          }
          

          TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
              public String getColumnName(int column) {
                  return columns.get(column);
              }

              @Override
              public int getRowCount() {
                  return list.size();
              }

              @Override
              public int getColumnCount() {
                  return columns.size();
              }

              @Override
              public Object getValueAt(int rowIndex, int columnIndex) {
                  try {
                      return getters.get(columnIndex).invoke(list.get(rowIndex));
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
              }
          };
          return model;

      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
}

