# Document Annotator

Backend for a web-based document annotation tool. Supports uploading and annotating **TXT**, **PDF**, and **DOCX** files
with user-defined labels. Labels can be organised in a hierarchy and linked to each other. The API provides CRUD for
documents, labels, label links, and annotations, plus simple reports.

## Functionality

- **Documents** – Upload files (TXT, PDF, DOCX) or create text documents via the API. List, get, download, or fetch
  content (TXT as text, DOCX as HTML). Deleting a document removes its annotations and the file from storage.
- **Labels** – Create, edit, and delete labels with name and color (hex). Labels can have a parent for a tree structure.
  List all labels flat or as roots only (frontend builds the tree from `parentLabelId`). Deleting a label is blocked if
  it is used in annotations or label links.
- **Label links** – Define directed relationships between two labels with an optional description (e.g. “related to”).
  Links are one-way. CRUD and filter by source or target label.
- **Annotations** – Mark content with a label. Two types:
    - **TEXT** – character range (`startOffset`, `endOffset`) for TXT documents.
    - **REGION** – bounding box (`pageNumber`, `x`, `y`, `width`, `height`) for PDF/DOCX or rendered HTML.
      Annotations are stored per document and label; list/filter by document, label, type, or page.
- **Reports** – Top labels (per document or global), label density per page (REGION annotations only), and a summary
  count of documents, labels, label links, and annotations.

## Tech stack

- Java 21, Spring Boot 4
- Spring Data JPA, PostgreSQL
- Apache POI (DOCX → HTML conversion)
- Bean Validation, DTOs with simple BeanUtils-based converters

## Run the app

**Prerequisites:** Java 21, Maven, PostgreSQL.

1. Create a database (e.g. `document_annotator`) and set connection details in `application.yml`.
2. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```
   Default port: **8080**.

## Configuration

In `src/main/resources/application.yml`:

- **Datasource** – `spring.datasource.url`, `username`, `password` for PostgreSQL.
- **Storage** – `app.storage.path` (default: `storage`) – directory where uploaded files are stored.

## API

REST API under `/api/`:

- **Documents** – `/api/documents` (upload, create text, list, get, content, html, download, annotations, delete).
- **Labels** – `/api/labels` (CRUD, list with `?tree=true` for roots).
- **Label links** – `/api/label-links` (CRUD, filter by `fromLabelId`, `toLabelId`).
- **Annotations** – `/api/annotations` (CRUD, filter by `documentId`, `labelId`, `type`, `pageNumber`).
- **Reports** – `/api/reports/top-labels`, `/api/reports/label-density`, `/api/reports/summary`.

CORS is allowed from all origins. Error responses use a common shape: `{ "error": "message" }` (and `fieldErrors` for
validation). Full endpoint and payload details: **[API.md](API.md)**.

## DOCX → HTML

DOCX documents can be converted to HTML for in-browser viewing and region annotation: **GET**
`/api/documents/{id}/html`. See **[DOCX_HTML_ENDPOINT.md](DOCX_HTML_ENDPOINT.md)** for details.
