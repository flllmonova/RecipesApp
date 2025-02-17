package org.example.recipesapp

object STUB {

    private val categories = listOf(
        Category(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "@assets/burger.png",
        ),
        Category(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "@assets/dessert.png",
        ),
        Category(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "@assets/pizza.png",
        ),
        Category(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "@assets/fish.png",
        ),
        Category(
            4,
            "Супы",
            "От классики до экзотики: мир в одной тарелке",
            "@assets/soup.png",
        ),
        Category(
            5,
            "Салаты",
            "Хрустящий калейдоскоп под соусом вдохновения",
            "@assets/salad.png",
        ),
    )

    fun getCategories() = categories
}