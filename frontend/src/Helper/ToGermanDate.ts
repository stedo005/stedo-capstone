export const toGermanDate = (date: string) => {
    let dateToFormat = new Date(date)
    let weekday
    if (dateToFormat.getDay() === 0) {
        weekday = "So"
    } else if (dateToFormat.getDay() === 1){
        weekday = "Mo"
    } else if (dateToFormat.getDay() === 2){
        weekday = "Di"
    } else if (dateToFormat.getDay() === 3){
        weekday = "Mi"
    } else if (dateToFormat.getDay() === 4){
        weekday = "Do"
    } else if (dateToFormat.getDay() === 5){
        weekday = "Fr"
    } else {
        weekday = "Sa"
    }
    return  weekday + " " + dateToFormat.toLocaleDateString()
}