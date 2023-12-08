package com.example.sbb9.article;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping("/article/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList" , articleList);

        return "article_list";
    }

    @GetMapping("/article/create")
    public String create() {
        return "article_form";
    }

    @PostMapping("/article/create")
    public String create2(@RequestParam String title , @RequestParam String content) {
        this.articleService.create(title , content);
        return "redirect:/article/list";
    }

    @GetMapping("/article/detail/{id}")
    public String detail(Model model, @PathVariable("id")Integer id){
       Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @GetMapping("/article/modify/{id}")
    public String modify(Model model, @PathVariable("id")Integer id, ArticleForm articleForm){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);

        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());

        return "article_form";
    }

    @PostMapping("/article/modify/{id}")
    public String modify(Model model, @PathVariable("id")Integer id, @Valid ArticleForm articleForm, BindingResult bindingResult){
        Article article = this.articleService.getArticle(id);

       // if(bindingResult.hasErrors()) {
          //  return "article_form";
        //}

        this.articleService.modify(article, articleForm.getTitle(),articleForm.getContent());

        return String.format("redirect:/article/detail/%s" , article.getId());



    }
    @GetMapping("article/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        Article article = this.articleService.getArticle(id);

        this.articleService.delete(article);

        return "redirect:/article/list";
    }
}
