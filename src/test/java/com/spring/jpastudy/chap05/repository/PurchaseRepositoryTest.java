package com.spring.jpastudy.chap05.repository;

import com.spring.jpastudy.chap05.entity.Goods;
import com.spring.jpastudy.chap05.entity.Purchase;
import com.spring.jpastudy.chap05.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = true)
class PurchaseRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    private User user1;
    private User user2;
    private User user3;
    private Goods goods1;
    private Goods goods2;
    private Goods goods3;


    @BeforeEach
    void setUp() {
        user1 = User.builder().name("망곰이").build();
        user2 = User.builder().name("하츄핑").build();
        user3 = User.builder().name("쿠로미").build();
        goods1 = Goods.builder().name("뚜비모자").build();
        goods2 = Goods.builder().name("닭갈비").build();
        goods3 = Goods.builder().name("중식도").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        goodsRepository.save(goods1);
        goodsRepository.save(goods2);
        goodsRepository.save(goods3);
    }


    @Test
    @DisplayName("유저와 상품을 연결한 구매 기록 생성 테스트")
    void createPurchaseTest() {
        //given
        Purchase purchase = Purchase.builder()
                .user(user2)
                .goods(goods1)
                .build();
        //when
        purchaseRepository.save(purchase);
        //then
        Purchase foundPurchase = purchaseRepository.findById(purchase.getId()).orElseThrow();

        System.out.println("\n\n\n구매한 회원정보: " + foundPurchase.getUser() + "\n\n");
        System.out.println("\n\n\n구매한 상품정보: " + foundPurchase.getGoods() + "\n\n");

        assertEquals(user2.getId(), foundPurchase.getUser().getId());
        assertEquals(goods1.getId(), foundPurchase.getGoods().getId());
    }



    @Test
    @DisplayName("특정 유저의 구매 목록을 조회한다")
    void findPurchaseListTest() {
        //given
        Purchase purchase1 = Purchase.builder()
                .user(user1).goods(goods1).build();
        Purchase purchase2 = Purchase.builder()
                .user(user1).goods(goods3).build();

        //when

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        //then

        User user = userRepository.findById(user1.getId()).orElseThrow();
        List<Purchase> purchases = user.getPurchaseList();

        assertEquals(2, purchases.size());
        assertTrue(purchases.stream().anyMatch(p -> p.getGoods().equals(goods1)));
        assertTrue(purchases.stream().anyMatch(p -> p.getGoods().equals(goods3)));
    }


    @Test
    @DisplayName("asd")
    void asdasd() {
        //given
        Purchase purchase1 = Purchase.builder()
                .user(user2).goods(goods1).build();
        Purchase purchase2 = Purchase.builder()
                .user(user3).goods(goods1).build();
        //when
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        Goods goods = goodsRepository.findById(goods1.getId()).orElseThrow();
        List<Purchase> purchases = goods.getPurchaseList();
        //then
        for (Purchase p : purchases) {
            System.out.printf("\n\n%s 상품을 구매한 유저명 : %s\n\n",
                    goods.getName(), p.getUser().getName());
        }

        assertEquals(2, purchases.size());
        assertTrue(purchases.stream().anyMatch(p -> p.getUser().equals(user2)));
        assertTrue(purchases.stream().anyMatch(p -> p.getUser().equals(user3)));
    }


    @Test
    @DisplayName("구매기록 삭제 테스트")
    void deletePurchaseTest() {
        //given
        Purchase purchase = Purchase.builder()
                .user(user1)
                .goods(goods1)
                .build();
        Purchase saved = purchaseRepository.save(purchase);
        //when
        purchaseRepository.delete(saved);
        //then
        Purchase foundPurchase = purchaseRepository.findById(purchase.getId()).orElse(null);
        assertNull(foundPurchase);
    }

}