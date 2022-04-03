export interface savedCategories {
    id: string
    categoryName: string
    itemsInCategory: Array<ItemInCategory>
}

export interface ItemInCategory {
    itemInCategory: string
}
