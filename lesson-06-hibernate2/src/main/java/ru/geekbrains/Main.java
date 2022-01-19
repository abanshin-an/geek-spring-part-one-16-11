package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.geekbrains.dao.DAO;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.dto.CartDTO;
import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.MerchantService;
import ru.geekbrains.services.ReportService;

import java.math.BigDecimal;
import java.util.List;

/**
 * В базе данных необходимо реализовать возможность хранить информацию о покупателях (id, имя) и товарах (id, название, стоимость).
 * У каждого покупателя свой набор купленных товаров;
 * Для обеих сущностей создаете Dao классы. Работу с SessionFactory выносите во вспомогательный класс;
 * * Создаете сервис, позволяющий по id покупателя узнать список купленных им товаров, и по id товара узнавать список покупателей этого товара;
 * ** Добавить детализацию по паре «покупатель — товар»: сколько стоил товар в момент покупки клиентом;
 */

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static final String NAME = "name";
    private static final String AMERICAN_CUSTOMER = "Arthur Clarke";
    private static final String CHINESE_CUSTOMER = "Liu Cixin";
    private static final String IPHONE = "iPhone";
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private DAO<Customer> customerDAO;
    @Autowired
    private ProductDAO productDAO;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String[] args) {
        Customer american = customerDAO.findOneByCriteria(new String[][]{{NAME, AMERICAN_CUSTOMER}}).orElseThrow();
        Customer chinese = customerDAO.findOneByCriteria(new String[][]{{NAME, CHINESE_CUSTOMER}}).orElseThrow();
        Product iPhone = productDAO.findByName( IPHONE);
        for (int i = 0; i < 3; i++) { // Цены растут с каждым циклом
            // Продали
            merchantService.    sell(chinese, new CartDTO[]{
                    new CartDTO(productDAO.findByName("Xiaomi"), 3L),
                    new CartDTO(productDAO.findByName("Huawei"), 4L)});
            merchantService.sell(american, new CartDTO[]{
                            new CartDTO(productDAO.findByName(IPHONE), 1L),
                            new CartDTO(productDAO.findByName("Pixel"), 2L)});
            // Переоценили товар
            revaluation(merchantService, productDAO.findAll());
            // Отчитались о продажах
            reportService.salesReport("Xiaomi").forEach(System.out::println);
            reportService.customerReport(AMERICAN_CUSTOMER).forEach(System.out::println);
            reportService.customerReport(CHINESE_CUSTOMER).forEach(System.out::println);
        }
    }

    private void revaluation(MerchantService merchantService, List<Product> products) {
        products.forEach(p -> merchantService.addPrice(p, new BigDecimal("1.1")));
        productDAO.save(products);
    }

}
