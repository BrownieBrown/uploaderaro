import React, {Component} from "react";
import UploadService from "../services/upload-files.service"
import {Button, Progress} from 'antd';

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

    componentDidMount() {
        UploadService.getFiles().then((response) => {
            this.setState({
                fileInfos: response.data
            })
        })
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
        const {
            selectedFiles,
            currentFile,
            progress,
            message,
            fileInfos
        } = this.state

        return (
            <div>
                {currentFile && (
                    <div className="progress">
                        <Progress className ="progress-bar" type="circle" percent={progress} />
                        {progress}%
                    </div>
                )}
                <label className="btn-default">
                    <input type="file" onChange={this.selectFile}/>
                </label>

                <Button>Upload</Button>
            </div>
        )
    }
}