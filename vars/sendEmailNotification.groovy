// lib (jenkins-shared-lib/vars/sendEmailNotification.groovy)

// Import the necessary utility class
import org.apache.commons.lang.StringEscapeUtils

def call(String buildStatus = 'SUCCESS', Map additionalLogs = [:]) {

    buildStatus = buildStatus ?: 'SUCCESS'

    def subject = ""
    def color = ""
    def message = ""

    if (buildStatus == 'SUCCESS') {
        subject = "✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#28a745"
        message = "The Jenkins pipeline completed successfully."
    } else if (buildStatus == 'FAILURE') {
        subject = "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#dc3545"
        message = "The pipeline failed. Please check the Jenkins logs for details."
    } else if (buildStatus == 'ABORTED') {
        subject = "⚠️ ABORTED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#ffc107"
        message = "The Jenkins pipeline was aborted manually."
    } else {
        subject = "ℹ️ ${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#17a2b8"
        message = "Pipeline finished with status: ${buildStatus}."
    }

    def logContent = ""
    if (!additionalLogs.isEmpty()) {
        logContent += "<hr/><h3>Detailed Logs:</h3>"
        additionalLogs.each { logTitle, filePath ->
            try {
                if (fileExists(filePath)) {
                    def fileText = readFile(filePath)
                    // Use StringEscapeUtils.escapeHtml instead of encodeAsHTML
                    def escapedText = StringEscapeUtils.escapeHtml(fileText)
                    logContent += "<h4>${logTitle} (${filePath}):</h4>"
                    logContent += "<pre style='background-color:#f8f8f8; padding:10px; border:1px solid #ddd; overflow-x:auto;'>${escapedText}</pre>"
                } else {
                    logContent += "<h4>${logTitle} (${filePath}):</h4>"
                    logContent += "<p style='color:red;'><i>Log file not found: ${filePath}</i></p>"
                }
            } catch (e) {
                logContent += "<h4>${logTitle} (${filePath}):</h4>"
                logContent += "<p style='color:red;'><i>Error reading log file: ${e.getMessage()}</i></p>"
            }
        }
    }


    def body = """
        <html>
        <body style="font-family:Arial, sans-serif;">
            <h2 style="color:${color};">${subject}</h2>
            <p>${message}</p>
            <ul>
                <li><b>Job:</b> ${env.JOB_NAME}</li>
                <li><b>Build Number:</b> ${env.BUILD_NUMBER}</li>
                <li><b>Triggered By:</b> ${env.BUILD_USER ?: 'Automated Trigger'}</li>
                <li><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
            </ul>
            ${logContent}
            <p>Regards,<br><b>Jenkins CI/CD System</b></p>
        </body>
        </html>
    """

    emailext(
        to: 'aslahea068@gmail.com',
        subject: subject,
        body: body,
        mimeType: 'text/html'
    )
    emailext(
        to: 'aslahea68@gmail.com',
        subject: subject,
        body: body,
        mimeType: 'text/html'
    )
    emailext(
        to: 'mprabiya6@gmail.com',
        subject: subject,
        body: body,
        mimeType: 'text/html'
    )
}
