import React, {Component} from "react";
import UploadService from "../services/upload-files.service"

export default class UploadFiles extends Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedFiles: undefined,
            currentFile: undefined,
            progress: 0,
            message: "",

            fileInfos: [],
        }
    }

    selectFile(event) {
        this.setState({
            selectedFiles: event.target.files
        })
    }

    upload() {
        let currentFile = this.state.selectedFiles[0]

        this.setState({
            progress: 0,
            currentFile: currentFile
        })

        UploadService.upload(currentFile, (event) => {
            this.setState({
                progress: Math.round(100 * event.loaded / event.total)
            })
        }).then((response) => {
            this.setState({
                message: response.data.message
            })
            return UploadService.getFiles()
        }).then((files) => {
            this.setState({
                fileInfos: files.data
            })
        }).catch(() => {
            this.setState({
                progress: 0,
                message: "Could not upload the file!",
                currentFile: undefined
            })
        })

        this.setState({
            selectedFiles: undefined
        })
    }

    render() {
    }
}