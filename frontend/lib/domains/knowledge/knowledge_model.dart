class NoteModel {
  final String id;
  final String title;
  final String content;
  final List<String> tags;
  final DateTime updatedAt;

  NoteModel({
    required this.id,
    required this.title,
    required this.content,
    required this.tags,
    required this.updatedAt,
  });
}
