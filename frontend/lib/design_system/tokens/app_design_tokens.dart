import 'dart:ui';
import 'package:flutter/material.dart';
import 'app_colors.dart';

@immutable
class AppDesignTokens extends ThemeExtension<AppDesignTokens> {
  final Color background;
  final Color surface;
  final Color primary;
  final Color secondary;
  final Color accent;
  final double cardRadius;
  final double glassBlur;

  const AppDesignTokens({
    required this.background,
    required this.surface,
    required this.primary,
    required this.secondary,
    required this.accent,
    this.cardRadius = 16.0,
    this.glassBlur = 12.0,
  });

  static const dark = AppDesignTokens(
    background: AppColors.background,
    surface: AppColors.surface,
    primary: AppColors.primary,
    secondary: AppColors.secondary,
    accent: AppColors.accent,
  );

  @override
  AppDesignTokens copyWith({
    Color? background,
    Color? surface,
    Color? primary,
    Color? secondary,
    Color? accent,
    double? cardRadius,
    double? glassBlur,
  }) {
    return AppDesignTokens(
      background: background ?? this.background,
      surface: surface ?? this.surface,
      primary: primary ?? this.primary,
      secondary: secondary ?? this.secondary,
      accent: accent ?? this.accent,
      cardRadius: cardRadius ?? this.cardRadius,
      glassBlur: glassBlur ?? this.glassBlur,
    );
  }

  @override
  AppDesignTokens lerp(ThemeExtension<AppDesignTokens>? other, double t) {
    if (other is! AppDesignTokens) return this;
    return AppDesignTokens(
      background: Color.lerp(background, other.background, t)!,
      surface: Color.lerp(surface, other.surface, t)!,
      primary: Color.lerp(primary, other.primary, t)!,
      secondary: Color.lerp(secondary, other.secondary, t)!,
      accent: Color.lerp(accent, other.accent, t)!,
      cardRadius: lerpDouble(cardRadius, other.cardRadius, t)!,
      glassBlur: lerpDouble(glassBlur, other.glassBlur, t)!,
    );
  }
}
