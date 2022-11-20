package info.scce.dime.app.game;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableImpl;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableRowImpl;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableEntryImplImpl;

import java.util.*;  
import java.util.stream.Collectors;  
/**
 * Collection of static methods for native SIBs
 */
public class Native {
	
	public static void sayHello(String name) {
		System.out.println("---------------------");
		System.out.println("  Hello " + name + "!");
		System.out.println("---------------------");
	}
	
	public static Table createGameBoardWordle(int width, int height) {
		
		// new table. 
		TableImpl table = new TableImpl();
		
		List<TableRowImpl> TableRowImplList = new ArrayList<TableRowImpl>(); 
		for (int x; x < width; x++) {
			TableRowImpl tableRow = new RowImpl();
			List<TableEntryImpl> TableEntryImplList = new ArrayList<TableEntryImpl>();
			for (int y; y < height; y++) {
				TableEntryImpl TableEntryImpl = new TableEntryImpl();
				TableEntryImpl.setvalue("1");
				TableEntryImplList.add(TableEntryImpl);
			}	
			
			tableRow.setTableEntryImpl_TableEntryImpl(TableEntryImplList);
			TableRowImplList.add(tableRow);
		}
		table.settableRow_TableRow(TableRowImplList);
		
		return table;
	}
}



