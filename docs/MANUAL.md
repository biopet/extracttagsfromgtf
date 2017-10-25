# Manual

ExtractTagsFromGtf can select tags from a refflat file and optionally filter for a certain feature.

Example:
```bash
java -jar ExtractTagsFromGtf-version \
-i inputRefflat
-o output.gtf
-t tag1 -t tag2 -t tag3
-f feauture
```