import 'package:flutter/foundation.dart';

/// Memory Domain Model representing a contextual Memory item.
@immutable
class MemoryModel {
  final String id;
  final String title;
  final String content;
  final String? summary;
  final String memoryType;
  final String importanceLevel;
  final String memoryStatus;
  final String sourceType;
  final String userId;
  final String? lifeProfileId;
  final DateTime createdAt;
  final DateTime updatedAt;
  final DateTime? archivedAt;
  final DateTime? deletedAt;
  final DateTime? lastAccessedAt;
  final int accessCount;
  final String language;
  final double confidenceScore;
  final bool isFavorite;
  final bool isPinned;
  final String? metadata;

  const MemoryModel({
    required this.id,
    required this.title,
    required this.content,
    this.summary,
    required this.memoryType,
    required this.importanceLevel,
    required this.memoryStatus,
    required this.sourceType,
    required this.userId,
    this.lifeProfileId,
    required this.createdAt,
    required this.updatedAt,
    this.archivedAt,
    this.deletedAt,
    this.lastAccessedAt,
    required this.accessCount,
    required this.language,
    required this.confidenceScore,
    required this.isFavorite,
    required this.isPinned,
    this.metadata,
  });

  factory MemoryModel.fromJson(Map<String, dynamic> json) {
    return MemoryModel(
      id: json['id'] as String,
      title: json['title'] as String,
      content: json['content'] as String,
      summary: json['summary'] as String?,
      memoryType: json['memoryType'] as String? ?? 'NOTE',
      importanceLevel: json['importanceLevel'] as String? ?? 'NORMAL',
      memoryStatus: json['memoryStatus'] as String? ?? 'ACTIVE',
      sourceType: json['sourceType'] as String? ?? 'MANUAL',
      userId: json['userId'] as String,
      lifeProfileId: json['lifeProfileId'] as String?,
      createdAt: DateTime.parse(json['createdAt'] as String),
      updatedAt: DateTime.parse(json['updatedAt'] as String),
      archivedAt: json['archivedAt'] != null ? DateTime.parse(json['archivedAt'] as String) : null,
      deletedAt: json['deletedAt'] != null ? DateTime.parse(json['deletedAt'] as String) : null,
      lastAccessedAt: json['lastAccessedAt'] != null ? DateTime.parse(json['lastAccessedAt'] as String) : null,
      accessCount: (json['accessCount'] as num?)?.toInt() ?? 0,
      language: json['language'] as String? ?? 'en',
      confidenceScore: (json['confidenceScore'] as num?)?.toDouble() ?? 1.0,
      isFavorite: json['favorite'] as bool? ?? false,
      isPinned: json['pinned'] as bool? ?? false,
      metadata: json['metadata'] as String?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'content': content,
      'summary': summary,
      'memoryType': memoryType,
      'importanceLevel': importanceLevel,
      'memoryStatus': memoryStatus,
      'sourceType': sourceType,
      'userId': userId,
      'lifeProfileId': lifeProfileId,
      'createdAt': createdAt.toIso8601String(),
      'updatedAt': updatedAt.toIso8601String(),
      'archivedAt': archivedAt?.toIso8601String(),
      'deletedAt': deletedAt?.toIso8601String(),
      'lastAccessedAt': lastAccessedAt?.toIso8601String(),
      'accessCount': accessCount,
      'language': language,
      'confidenceScore': confidenceScore,
      'favorite': isFavorite,
      'pinned': isPinned,
      'metadata': metadata,
    };
  }
}
