package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();

    }

    @Test
    public void list() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));
        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);

    }

    @Test
    public void getById() {
        given(categoryRepository.findById(ArgumentMatchers.anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));
        webTestClient.get()
                .uri("/api/v1/categories/ssss")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void create() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("desc").build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Cat").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void update() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("desc").build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Cat").build());

        webTestClient.put()
                .uri("/api/v1/categories/saassasa")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patch() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Cat").build());

        webTestClient.patch()
                .uri("/api/v1/categories/saassasa")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
        verify(categoryRepository).save(any());
    }

    @Test
    public void patchWithNoChanges() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri("/api/v1/categories/saassasa")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
        verify(categoryRepository, never()).save(any());
    }
}