package com.example.domain_models.theme

enum class ViewScaleDomain(
    val id: Int,
    val textMultiplier: Float,
) {
    M(0, 1f),
    L(1, 1.1f),
    XL(2, 1.3f);

    companion object {
        fun getById(id: Int): ViewScaleDomain {
            return entries.find { it.id == id } ?: M
        }

        fun getByName(name: String): ViewScaleDomain {
            return entries.find { it.name == name } ?: M
        }
    }
}

fun getMin(viewScale1: ViewScaleDomain, viewScale2: ViewScaleDomain?): ViewScaleDomain {
    return if (viewScale2 != null && viewScale2.textMultiplier < viewScale1.textMultiplier) viewScale2 else viewScale1
}
