import java.util.LinkedList;
import java.util.*;

class InvItem {
	private int quantity;
	private int cost;
	private String name;

	public InvItem (int quantity, int cost, String name) {
		this.quantity = quantity;
		this.cost = cost;
		this.name = name;
	}

	public int getQuantity () {
		return quantity;
	}

	public int getCost () {
		return cost;
	}

	public String getName() {
		return name;
	}

	public void setQuantity (int quantity) {
		this.quantity = quantity;
	}

	public void print() {
		System.out.println("Name: " + name);
		System.out.println("Qty: " + quantity);
		System.out.println("Cost: " + cost);
	}
}

class Lifo {
	LinkedList stack = new LinkedList();
	String name;

	public Lifo (String name) {
		this.name = name;
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public void push (InvItem n) {
		stack.addFirst(n);
	}

	public InvItem pop () {
		return (InvItem) stack.remove();
	}

	public InvItem peek() {
		return (InvItem) stack.get(0);
	}

	public int size() {
		return stack.size();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return "Lifo";
	}

	public int totalQuantity () {
		int total = 0;
		for (int i = 0; i < stack.size(); i++) {
			InvItem item = (InvItem) stack.get(i);
			int qty = item.getQuantity();
			total += qty;
		}
		return total;
	}

	public void printString() {
		System.out.println("\n" + this.name);
		for (int i = 0; i < stack.size(); i++) {
			InvItem inv_item = (InvItem) stack.get(i);
			inv_item.print();
		}
	}
}

class Fifo {

	LinkedList queue = new LinkedList();
	String name;

	public Fifo (String name) {
		this.name = name;
	}

	// Is our queue empty?
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	// Enqueueing an item
	public void enqueue(InvItem n) {
		queue.addLast(n);
	}

	// Dequeueing an item
	public InvItem dequeue() {
		return (InvItem) queue.remove(0);
	}

	// Peek first item
	public InvItem peek() {
		return (InvItem) queue.get(0);
	}

		// Size of queue
	public int size() {
		return queue.size();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return "Fifo";
	}

	public int totalQuantity () {
		int total = 0;
		for (int i = 0; i < queue.size(); i++) {
			InvItem item = (InvItem) queue.get(i);
			int qty = item.getQuantity();
			total += qty;
		}
		return total;
	}

	public void printString() {
		System.out.println("\n" + this.name);
		for (int i = 0; i < queue.size(); i++) {
			InvItem inv_item = (InvItem) queue.get(i);
			inv_item.print();
		}
	}
}

public class WorldsCollide2 {	

	public static void main(String[] args) {

		HashMap<String, Lifo> lifoMap = new HashMap<String, Lifo>();
		HashMap<String, Fifo> fifoMap = new HashMap<String, Fifo>();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nOPTIONS" + "\n1. Create new account" +
			"\n2. Record purchase" + "\n3. Record sale" + "\n4. Display summary of all accounts" +
			"\n5. View remaining balance of an account" + "\n6. Exit");
		System.out.print("\nSELECT AN OPTION: ");
		int input = sc.nextInt();
		//System.out.println("You have selected: " + input);

		while (input != 6) {
			if (input == 1) {
				System.out.println("\nSelect your inventory system \n1. FIFO \n2. LIFO");
				int system_number = sc.nextInt();
				System.out.print("Account Name: ");
				sc.nextLine();
				String new_account_name = sc.nextLine();
				
				if (system_number == 1) {
					Fifo new_fifo = new Fifo(new_account_name);
					fifoMap.put(new_account_name, new_fifo);
				}

				else {
					Lifo new_lifo = new Lifo(new_account_name);
					lifoMap.put(new_account_name, new_lifo);
				}
			}

			else if (input == 2) {
				//creating test account
				//Fifo new_fifo = new Fifo("Markers");
				//fifoMap.put("Markers", new_fifo);

				System.out.print("\nName of Account you are debiting: ");
				sc.nextLine();
				String account_name = sc.nextLine();

				System.out.println("Which Inventory System does the " + account_name + " account use?" + "\n1. FIFO \n2. LIFO");
				int selection = sc.nextInt();
				
				System.out.print("Item quantity: ");
				int item_quant = sc.nextInt();

				System.out.print("Item cost: ");
				int item_cost = sc.nextInt();

				if (selection == 1) {
					String id = Long.toString(System.currentTimeMillis());
					InvItem new_item = new InvItem(item_quant, item_cost, id);
					Fifo fifo_item = fifoMap.get(account_name);
					fifo_item.enqueue(new_item);
					fifo_item.printString();
				}

				else {
					String id = Long.toString(System.currentTimeMillis());
					InvItem new_item = new InvItem(item_quant, item_cost, id);
					Lifo lifo_item = lifoMap.get(account_name);
					lifo_item.push(new_item);
					lifo_item.printString();
				}
			}

			else if (input == 3) {
				System.out.print("\nName of Account you are crediting: ");
				sc.nextLine();
				String name_credit = sc.nextLine();

				System.out.println("Which Inventory System does the " + name_credit + " account use?" + "\n1. FIFO \n2. LIFO");
				int selection_credit = sc.nextInt();
				
				System.out.print("Item quantity: ");
				int qty_credit = sc.nextInt();

				if (selection_credit == 1) {
					Fifo fifo_credit = fifoMap.get(name_credit);
					if (fifo_credit.totalQuantity() < qty_credit) {
						System.out.println("Inventory shortage!");
					}

					else {
						int starting_qty = qty_credit;
						while (starting_qty != 0) {
							InvItem first_item = fifo_credit.dequeue();
							int item_qty = first_item.getQuantity();
							if (item_qty > starting_qty) {
								first_item.setQuantity(item_qty - starting_qty);
								starting_qty = 0;
								fifo_credit.enqueue(first_item);
							}
							// else if (item_qty == starting_qty) {
							// 	starting_qty = 0;
							//}
							else {
								starting_qty -= item_qty;
							}
						}
					}
				//fifo_credit.printString();
				}

				else {
					Lifo lifo_credit = lifoMap.get(name_credit);
					if (lifo_credit.totalQuantity() < qty_credit) {
						System.out.println("Inventory shortage!");
					}

					else {
						int starting_qtyl = qty_credit;
						while (starting_qtyl != 0) {
							InvItem first_iteml = lifo_credit.pop();
							int item_qtyl = first_iteml.getQuantity();
							if (item_qtyl > starting_qtyl) {
								first_iteml.setQuantity(item_qtyl - starting_qtyl);
								starting_qtyl = 0;
								lifo_credit.push(first_iteml);
							}
							// else if (item_qty == starting_qty) {
							// 	starting_qty = 0;
							//}
							else {
								starting_qtyl -= item_qtyl;
							}
						}
					}
				lifo_credit.printString();
				}
			} // closes option 3

			else if (input == 4) {
				System.out.println("\nFIFO Accounts");
				Set set = fifoMap.entrySet(); //wtf is this
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry)iterator.next();
					System.out.println("Account name: " + entry.getKey());
				}
				System.out.println("\nLIFO Accounts");
				Set setl = lifoMap.entrySet(); //wtf is this
				Iterator iteratorl = setl.iterator();
				while (iteratorl.hasNext()) {
					Map.Entry entryl = (Map.Entry)iteratorl.next();
					System.out.println("Account name: " + entryl.getKey());
				}
			}

			else {
				System.out.print("\nAccount to view: ");
				sc.nextLine();
				String account = sc.nextLine();
				System.out.println("Which Inventory System? \n1. FIFO \n2. LIFO");
				int selection = sc.nextInt();

				if (selection == 1) {
					Fifo selected_accountf = fifoMap.get(account);
					selected_accountf.printString();
				}

				else {
					Lifo selected_accountl = lifoMap.get(account);
					selected_accountl.printString();
				}
			}

			System.out.println("-------------------------------");
			System.out.println("\nOPTIONS" + "\n1. Create new account" +
				"\n2. Record purchase" + "\n3. Record sale" + "\n4. Display list of accounts" +
				"\n5. View inventory in an account" + "\n6. Exit");
			System.out.print("\nSELECT AN OPTION: ");
			input = sc.nextInt();
			//System.out.println("You have selected: " + input);
		} // closes while loop
	}
}