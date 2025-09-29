package com.example.unit4_pathway3_mycity.data

import com.example.unit4_pathway3_mycity.R


object PlaceRepository {
    val categories = listOf("Café", "Nhà hàng", "Công viên", "Trung tâm mua sắm")

    val places = listOf(
        Place(1, "Highlands Coffee", "Chuỗi quán cà phê nổi tiếng", R.drawable.cafe, "Café"),
        Place(2, "Nhà hàng Sen", "Buffet phong phú nhiều món Việt", R.drawable.restaurant, "Nhà hàng"),
        Place(3, "Công viên Thống Nhất", "Không gian xanh giữa lòng Hà Nội", R.drawable.park, "Công viên"),
        Place(4, "Vincom Center", "Trung tâm thương mại hiện đại", R.drawable.mall, "Trung tâm mua sắm")
    )
}
