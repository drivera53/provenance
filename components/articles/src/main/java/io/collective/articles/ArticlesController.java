package io.collective.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.restsupport.BasicHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

public class ArticlesController extends BasicHandler {
    private final ArticleDataGateway gateway;

    public ArticlesController(ObjectMapper mapper, ArticleDataGateway gateway) {
        super(mapper);
        this.gateway = gateway;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        get("/articles", List.of("application/json", "text/html"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *all* articles, map record to infos, and send back a collection of article infos
                List<ArticleRecord> articleRecord_list = gateway.findAll();
                List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
                // Iterating over the articleRecord_list
                for (int i = 0; i < articleRecord_list.size(); i++) {
                    ArticleRecord curr_articleRecord = articleRecord_list.get(i);
                    ArticleInfo curr_articleInfo = new ArticleInfo(curr_articleRecord.getId(), curr_articleRecord.getTitle());
                    articles.add(curr_articleInfo);
                }
                writeJsonBody(servletResponse, articles);

            }
        });

        get("/available", List.of("application/json"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *available* articles, map records to infos, and send back a collection of article infos
                List<ArticleRecord> articleRecord_list = gateway.findAvailable();
                List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
                // Iterating over the articleRecord_list
                for (int i = 0; i < articleRecord_list.size(); i++) {
                    ArticleRecord curr_articleRecord = articleRecord_list.get(i);
                    ArticleInfo curr_articleInfo = new ArticleInfo(curr_articleRecord.getId(), curr_articleRecord.getTitle());
                    articles.add(curr_articleInfo);
                }
                writeJsonBody(servletResponse, articles);
            }
        });
    }
}
