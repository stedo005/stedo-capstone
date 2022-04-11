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
    invoiceDate: string
    itemName: string
    itemPrice: number
    itemQuantity: number
    totalPrice: number
}

export interface user {
    username: string
    lastUpdate: string
}

