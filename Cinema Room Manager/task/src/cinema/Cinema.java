package cinema;

import java.util.Scanner;

public class Cinema {

	static Scanner scanner = new Scanner(System.in);
	// numbers used to create theatre map
	static int gridRows = defineGridRows();
	static int gridColumns = defineGridColumns();
	static String[][] cinemaTheatre = defineCinema();

	// actual number of seats
	static int rowsOfSeats = gridRows - 1;
	static int seatsPerRow = gridColumns - 1;
	static int seatsInTotal = (seatsPerRow) * (rowsOfSeats);
	static int frontRows = ((rowsOfSeats) % 2 == 0) ?
			(rowsOfSeats / 2) :
			((rowsOfSeats - 1) / 2);

	// variables for statistics
	static int totalNumberOfTicketsPurchased;
	static int currentIncome;

	// generate the cinema theatre along with row & column coordinate labels
	public static int defineGridRows() {
		System.out.println("Enter the number of rows:");
		return scanner.nextInt() + 1;
	}

	static public int defineGridColumns() {
		System.out.println("Enter the number of seats in each row:");
		return scanner.nextInt() + 1;
	}

	public static String[][] defineCinema() {
		String[][] cinemaTheatre = new String[gridRows][gridColumns];
		for (int row = 0; row < (gridRows); row++) {
			for (int seat = 0; seat < (gridColumns); seat++) {
				if (row == 0 && seat == 0) { // print blank corner
					cinemaTheatre[row][seat] = "  ";
				} else if (row == 0) { // print seat numbers
					cinemaTheatre[row][seat] = "" + seat + " ";
				} else if (seat == 0) { // print row numbers
					cinemaTheatre[row][seat] = "" + row + " ";
				} else { // vacant seats are marked as 'S'
					cinemaTheatre[row][seat] = "S ";
				}
			}
		}
		return cinemaTheatre;
	}

	// main method which simply calls the menu
	public static void main(String[] args) {
		displayMainMenu(cinemaTheatre);
	}

	// menu locked in a while loop
	public static void displayMainMenu(String[][] cinemaTheatre) {
		boolean mainFlag = true;
		while (mainFlag) {
			System.out.println("\n1. Show the seats");
			System.out.println("2. Buy a ticket");
			System.out.println("3. Statistics");
			System.out.println("0. Exit");

			int userSelection = scanner.nextInt();
			switch (userSelection) {
				case 1:
					showTheSeats(cinemaTheatre);
					break;
				case 2:
					buyTicket(cinemaTheatre);
					break;
				case 3:
					displayStatistics(cinemaTheatre);
					break;
				case 0:
					mainFlag = false;
					break;
			}
		}
	}

	// display the theatre
	public static void showTheSeats(String[][] cinemaTheatre) {
		System.out.println("\nCinema:");
		for (String[] strings : cinemaTheatre) {
			for (String string : strings) {
				System.out.print(string);
			}
			System.out.println();
		}
	}

	// get user input, check availability, reserve and display price
	public static void buyTicket(String[][] cinemaTheatre) {
		System.out.println("\nEnter a row number:");
		int clientRow = (scanner.nextInt());
		System.out.println("Enter a seat number in that row:");
		int clientSeat = (scanner.nextInt());
		if (clientRow <= 0 || clientSeat <= 0) {
			System.out.println("Wrong input!");
			buyTicket(cinemaTheatre);
		} else if (clientRow > (rowsOfSeats) || clientSeat > (seatsPerRow)) {
			System.out.println("Wrong input!");
			buyTicket(cinemaTheatre);
		} else if (cinemaTheatre[clientRow][clientSeat].equals("B ")) {
			System.out.println("That ticket has already been purchased!");
			buyTicket(cinemaTheatre);
		} else {
			cinemaTheatre[clientRow][clientSeat] = "B ";
			totalNumberOfTicketsPurchased++;

			if (seatsInTotal <= 60 || clientRow <= frontRows) {
				System.out.println("Ticket price: $10");
				currentIncome = currentIncome + 10;
			} else {
				System.out.println("Ticket price: $8");
				currentIncome = currentIncome + 8;
			}
		}
	}

	// display number of tickets sold, percentage of tickets sold, current sales and potential sales
	public static void displayStatistics(String[][] cinemaTheatre) {
		int purchasedTickets = countPurchasedTickets(cinemaTheatre);
		System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
		double percentageOfTicketSold = 100 * ((double) totalNumberOfTicketsPurchased / (double) seatsInTotal);
		System.out.println("Percentage: " + String.format("%.2f", percentageOfTicketSold) + "%");
		System.out.println("Current income: $" + currentIncome);
		int totalPotentialIncome = seatsInTotal < 60
				? seatsInTotal * 10
				: (frontRows * seatsPerRow * 10) + ((rowsOfSeats - frontRows) * seatsPerRow * 8);
		System.out.println("Total income: $" + totalPotentialIncome);
	}

	// method to aid statistics
	public static int countPurchasedTickets(String[][] cinemaTheatre) {
		int purchasedTickets = 0;
		for (String[] strings : cinemaTheatre) {
			for (String string : strings) {
				if (string.equals("B ")) {
					purchasedTickets++;
				}
			}
		}
		return purchasedTickets;
	}
}