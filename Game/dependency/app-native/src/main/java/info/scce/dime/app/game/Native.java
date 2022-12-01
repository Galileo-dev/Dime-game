package info.scce.dime.app.game;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableRowController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableEntryController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.ValidationController;

import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.Table;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableRow;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableEntry;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.Validation;


import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;  
import java.util.stream.Collectors;

import static java.lang.System.exit;



/**
 * Collection of static methods for native SIBs
 */
@ApplicationScoped
public class Native {
	@Inject
	protected TableController tController;
	@Inject
	protected TableRowController trController;
	@Inject
	protected TableEntryController teController;
	@Inject
	protected ValidationController validController;
	
	public static void setRow(TableRow tableRow, String word) {
		String[] wordArray = word.split("(?!^)");
		List<TableEntry> tableEntrys = tableRow.gettableEntry_TableEntry();
		for (int i=0; i<wordArray.length; i++) {
			TableEntry currentTableEntry = tableEntrys.get(i);
			currentTableEntry.setvalue(wordArray[i]);
		}
	}


	public static Table createGameBoardWordle(long width, long height) {
		Native one = new Native();
		return one.init(width, height);

	}
	public Table init(long width, long height){
			Table table = getBean(TableController.class).create(null);


		List<TableRow> tableRowList = new ArrayList<TableRow>();
		for (int x = 0; x < width; x++) {
			TableRow tableRow = getBean(TableRowController.class).create(null);
			System.out.println("inside for loop x =" + x);
			List<TableEntry> tableEntryList = new ArrayList<TableEntry>();
			for (int y = 0; y < height; y++) {

				System.out.println("inside nested for loop y =" + y);
				TableEntry tableEntry = getBean(TableEntryController.class).create(null);
				tableEntry.setvalue("");
				Validation validation = Validation.fullMatch;
				tableEntry.setvalidation(validation);
				tableEntryList.add(tableEntry);
			}

			tableRow.settableEntry_TableEntry(tableEntryList);
			tableRowList.add(tableRow);
		}
		table.settableRow_TableRow(tableRowList);

		return table;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getBean(Class<T> clazz) {
		final BeanManager beanManager = CDI.current().getBeanManager();
		
		final Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(clazz));

		final CreationalContext<T> cctx = beanManager.createCreationalContext(bean);

		return (T) beanManager.getReference(bean, bean.getBeanClass(), cctx);
	}
	
	public static boolean isInList(int index, Object list) {
        return index >= 0 && index < ((List) list).size();
    }
	
    public static TableRow getNextRow(TableRow tableRow, List<TableRow> tableRows) {
        int index = tableRows.indexOf(tableRow) + 1;
       
        return isInList(index, tableRows) ? tableRows.get(index) : null;
    }


}