import React from "react";
import './App.css'
import DropZone from "./dropzone/DropZone";

function App() {
    return (
        <div>
            <p className="title">Upload your image</p>
            <div className="content">
                <DropZone />
            </div>
        </div>
    )
}

export default App