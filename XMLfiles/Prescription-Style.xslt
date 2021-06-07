<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b><i>Prescription</i>:</b></p>
   <p> - <u>Drug/Treatment</u>: <xsl:value-of select="//name" /></p>
   <p> - <u>Doses</u>:  <xsl:value-of select="//doses" /></p>
   <p> - <u>Duration</u>:  <xsl:value-of select="//@duration" /></p>
   <p> - <u>Notes</u>: <xsl:value-of select="//@notes" /></p>
            
 
   </html>
</xsl:template>

</xsl:stylesheet>