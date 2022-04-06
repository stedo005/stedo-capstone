export interface savedCategories {
    id: string
    categoryName: string
    itemsInCategory: Array<string>
}

export interface soldItem {
    id: string
    invoiceNumber: string
    invoiceTimestamp: string
    itemName: string
    itemPrice: string
    itemQuantity: string
}

