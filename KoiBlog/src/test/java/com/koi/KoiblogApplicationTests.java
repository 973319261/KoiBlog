package com.koi;

import com.koi.entity.Article;
import com.koi.mapper.ArticleMapper;
import com.koi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KoiblogApplicationTests {
    @Autowired
    ArticleService articleService;
    @Test
    public void contextLoads() throws Exception{
      //  articleService.saveArticle(new Article());
    }

}
