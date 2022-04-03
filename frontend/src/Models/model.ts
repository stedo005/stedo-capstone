export interface Category {
    id: string
    categoryName: string
    itemsInCategory: Array<ItemInCategory>
}

export interface ItemInCategory {
    itemInCategory: string
}
