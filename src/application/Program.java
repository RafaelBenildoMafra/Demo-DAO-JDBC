package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {

	//NAO DA ACESSO A IMPLEMENTA��O SO A INTERFACE
		SellerDao sellerDao = DaoFactory.createSellerDao();		
		
		System.out.println("TESTE 1: seller findById");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
	}

}
