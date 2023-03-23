package com.javatechie.aws.cicd.example.java8;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankTransactionExample {

	public static List<BankTransaction> transactions = getTransactions();

	public static void main(String[] args) {
		
		long transactionofday=getTotalTransactionToday();
		System.out.println("Total transactions :" +transactionofday);
		LocalDate d=LocalDate.now();
		getMaxTransaction(d);
		getSmallTransaction(d);
		getAvgSalary(d);
		getSumOfSalary(d);
		
	    getSortedEmployees();
		
		getSecondMostSalary();
	}
	
	public static long getTotalTransactionToday()
	{
		return transactions.parallelStream().filter(t->t.getDate().equals(LocalDate.now())).count();
		
	}
	
	//find the maximum indivisual transaction on a day
	public static Optional<BankTransaction> getMaxTransaction(LocalDate when)
	{
		return transactions.parallelStream().filter(t->t.getDate().equals(when)).max(Comparator.comparing(BankTransaction::getAmount));
	}
	
	public static Optional<BankTransaction> getSmallTransaction(LocalDate when)
	{
		return transactions.parallelStream().filter(t->t.getDate().equals(when)).min(Comparator.comparing(BankTransaction::getAmount));
	}
	
	//average salary of the transactions
	public static double getAvgSalary(LocalDate date)
	{
		return transactions.parallelStream().filter(t->t.getDate().equals(date)).map(e->e.getAmount()).mapToDouble(i->i).average().getAsDouble();
	}

	//sum of all the amount of one day
	public static double getSumOfSalary(LocalDate date)
	{
		return transactions.parallelStream().filter(t->t.getDate().equals(date)).map(e->e.getAmount()).mapToDouble(i->i).sum();
	}
	
	//find the sorted list of accountnumber
	public static void getSortedEmployees()
	{
		 transactions.parallelStream().sorted(Comparator.comparing(BankTransaction::getAccNumber)).forEach(System.out::println);
	}
	
	//find the second most amount of account
	public static void getSecondMostSalary()
	{
		Optional<BankTransaction> secondHighestSalaryEmployee =
				transactions.parallelStream()
                        .sorted(Comparator.comparingDouble(BankTransaction::getAmount).reversed())
                        .skip(1)
                        .findFirst();
		
		 System.out.println("Exercise 13 : Second Highest Salary Employee: "+secondHighestSalaryEmployee.get());

	}
	
	private static List<BankTransaction> getTransactions() {
		return Arrays.asList(new BankTransaction("123", 250.00, LocalDate.now()),
				new BankTransaction("456", 1250.00, LocalDate.now().minusDays(1)),
				new BankTransaction("789", 2250.00, LocalDate.now()),
				new BankTransaction("987", 550.00, LocalDate.now()),
				new BankTransaction("654", 750.00, LocalDate.now()),
				new BankTransaction("123", 4350.00, LocalDate.now()),
				new BankTransaction("789", 150.00, LocalDate.now()),
				new BankTransaction("456", 4250.00, LocalDate.now()),
				new BankTransaction("987", 50.00, LocalDate.now()),
				new BankTransaction("456", 850.00, LocalDate.now().minusDays(2)));
	}

}

class BankTransaction {

	private String accNumber;
	private double amount;
	private LocalDate date;

	public BankTransaction(String accNo, double amount, LocalDate date) {
		this.accNumber = accNo;
		this.amount = amount;
		this.date = date;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}
}