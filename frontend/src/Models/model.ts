export interface savedCategories {
    id: string
    categoryName: string
    itemsInCategory: Array<string>
}

export interface result {
    sumOfAllItems: number
    soldItems: soldItem[]
}

export interface soldItem {
    id: string
    invoiceNumber: string
    invoiceTimestamp: string
    itemName: string
    itemPrice: number
    itemQuantity: number
    totalPrice: number
}

