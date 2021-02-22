Secured RESTFUL API to create and list articles:
- To create an article the user should provide a title, author, the content and date for publishing.
- All of the fields are mandatory and the title should not exceed 100 characters. The publishing date should bind to ISO 8601 format.
- Article results should be paginated.

Additionally, endpoint for statistics that is be accessible only by admins is provided:
- The endpoint returns count of published articles on daily bases for the 7 days.

The functionality is covered by unit tests.
