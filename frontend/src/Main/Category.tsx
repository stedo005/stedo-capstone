import {useParams} from "react-router-dom";

const Category = () => {

    const linkedId = useParams()

    return(

        <div>
            Category mit id: {linkedId.categoryId}
        </div>

    )

}

export default Category