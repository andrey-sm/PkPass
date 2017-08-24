package pro.smartum.pkpass.function

class FileUnzipControllerSpec(val zipFileString: String, spec: UnzipPassController.InputStreamUnzipControllerSpec) : UnzipControllerSpec(spec.targetPath, spec.context, spec.passStore, spec.onSuccessCallback, spec.failCallback) {
    val source: String = spec.inputStreamWithSource.source

    init {
        overwrite = spec.overwrite
    }
}
