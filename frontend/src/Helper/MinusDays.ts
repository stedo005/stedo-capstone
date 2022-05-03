export const minusDays = (date: string, daysToSubtract: number) => {
    let dateToReduce = new Date(date)
    let days = daysToSubtract * 86400000
    let milliseconds = dateToReduce.getTime()
    let formattedDate = new Date(milliseconds - days)
    return formattedDate.toISOString().slice(0, 10)
}