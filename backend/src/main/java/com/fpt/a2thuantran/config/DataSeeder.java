package com.fpt.a2thuantran.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fpt.a2thuantran.entity.Category;
import com.fpt.a2thuantran.entity.NewsArticle;
import com.fpt.a2thuantran.entity.SystemAccount;
import com.fpt.a2thuantran.entity.Tag;
import com.fpt.a2thuantran.repository.CategoryRepository;
import com.fpt.a2thuantran.repository.NewsArticleRepository;
import com.fpt.a2thuantran.repository.SystemAccountRepository;
import com.fpt.a2thuantran.repository.TagRepository;

/**
 * This class runs once every time the application starts.
 * If the database is empty, it creates a few sample accounts, categories and
 * news articles so the application can be tested right away.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final SystemAccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(SystemAccountRepository accountRepository,
                       CategoryRepository categoryRepository,
                       NewsArticleRepository newsArticleRepository,
                       TagRepository tagRepository,
                       PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.newsArticleRepository = newsArticleRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (accountRepository.count() > 0) {
            // data already exists, do not seed again
            return;
        }

        SystemAccount admin = new SystemAccount();
        admin.setAccountName("System Admin");
        admin.setAccountEmail("admin@fu.edu.vn");
        admin.setAccountRole(SystemAccount.ROLE_ADMIN);
        admin.setAccountPassword(passwordEncoder.encode("Admin@123"));
        accountRepository.save(admin);

        SystemAccount staff = new SystemAccount();
        staff.setAccountName("Nguyen Van Staff");
        staff.setAccountEmail("staff@fu.edu.vn");
        staff.setAccountRole(SystemAccount.ROLE_STAFF);
        staff.setAccountPassword(passwordEncoder.encode("Staff@123"));
        accountRepository.save(staff);

        Category sportCategory = new Category();
        sportCategory.setCategoryName("The thao");
        sportCategory.setCategoryDescription("Tin tuc the thao");
        sportCategory.setIsActive(true);
        categoryRepository.save(sportCategory);

        Category technologyCategory = new Category();
        technologyCategory.setCategoryName("Cong nghe");
        technologyCategory.setCategoryDescription("Tin tuc cong nghe");
        technologyCategory.setIsActive(true);
        categoryRepository.save(technologyCategory);

        Tag sampleTag = tagRepository.save(new Tag("FPT"));

        NewsArticle sampleNews = new NewsArticle();
        sampleNews.setNewsTitle("Chao mung ban den voi he thong quan ly tin tuc");
        sampleNews.setHeadline("Bai viet mau");
        sampleNews.setNewsContent("Day la noi dung bai viet mau duoc tao tu dong khi khoi dong ung dung lan dau.");
        sampleNews.setNewsSource("FU News Management System");
        sampleNews.setCategory(technologyCategory);
        sampleNews.setNewsStatus(true);
        sampleNews.setCreatedBy(staff);
        sampleNews.setCreatedDate(LocalDateTime.now());
        sampleNews.setTags(List.of(sampleTag));
        newsArticleRepository.save(sampleNews);

        System.out.println("===================================================");
        System.out.println("Da tao du lieu mau:");
        System.out.println("  Admin : admin@fu.edu.vn / Admin@123");
        System.out.println("  Staff : staff@fu.edu.vn / Staff@123");
        System.out.println("===================================================");
    }
}
