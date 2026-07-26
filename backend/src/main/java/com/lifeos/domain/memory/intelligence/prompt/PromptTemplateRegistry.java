package com.lifeos.domain.memory.intelligence.prompt;

/**
 * Enterprise Versioned Prompt Templates for Memory Intelligence Engine.
 */
public class PromptTemplateRegistry {

    public static final String V1_MEMORY_EXTRACTION = """
            System: You are LifeOS AI Memory Intelligence Engine. Your task is to analyze the unstructured input text and extract a rich, structured memory JSON.
            Return ONLY valid JSON matching this schema:
            {
              "title": "Short title",
              "summary": "Concise summary",
              "content": "Original content or expanded text",
              "memoryType": "NOTE | TASK | GOAL | PROJECT | PERSON | EVENT | DOCUMENT | HEALTH | FINANCE | HABIT | KNOWLEDGE | REMINDER | DECISION | PREFERENCE | OBSERVATION | CONVERSATION | CUSTOM",
              "importanceLevel": "LOW | NORMAL | HIGH | CRITICAL",
              "importanceScore": 0-100,
              "importanceReasoning": "Explanation",
              "entities": [ {"type": "PERSON|TIME|DATE|ORG|LOCATION", "value": "text", "confidence": 0.0-1.0} ],
              "confidence": 0.0-1.0,
              "language": "en",
              "tags": ["tag1", "tag2"],
              "source": "MANUAL",
              "relatedTopics": ["topic1"],
              "suggestedCategory": "Category",
              "suggestedPriority": "HIGH",
              "suggestedReminders": ["reminder"],
              "followUpActions": ["action"]
            }

            Input text: "%s"
            """;

    public static final String V1_CLASSIFICATION = """
            System: Classify the following memory input into one of these types:
            NOTE, TASK, GOAL, PROJECT, PERSON, EVENT, DOCUMENT, HEALTH, FINANCE, HABIT, KNOWLEDGE, REMINDER, DECISION, PREFERENCE, OBSERVATION, CONVERSATION, CUSTOM.
            Return JSON:
            { "memoryType": "TYPE", "confidence": 0.95 }

            Input: "%s"
            """;

    public static final String V1_SUMMARIZATION = """
            System: Generate short, medium, and executive summaries for the input text.
            SUMMARIZE
            Return JSON:
            { "shortSummary": "...", "mediumSummary": "...", "executiveSummary": "..." }

            Input: "%s"
            """;

    public static final String V1_TAGS = """
            System: Generate 3 to 10 relevant lowercase tags for the input text.
            TAGS
            Return JSON:
            { "tags": ["tag1", "tag2", "tag3"] }

            Input: "%s"
            """;

    public static final String V1_ENTITIES = """
            System: Extract named entities (Person, Org, Location, Date, Time, Money, Email, Phone) from the input text.
            ENTITIES
            Return JSON:
            { "entities": [ {"type": "PERSON", "value": "Name", "confidence": 0.95} ] }

            Input: "%s"
            """;

    public static final String V1_DUPLICATE_CHECK = """
            System: Compare the target text with existing memory entries. Determine if it is DUPLICATE, SIMILAR, RELATED, or UNIQUE.
            DUPLICATE_CHECK
            Return JSON:
            { "status": "DUPLICATE|SIMILAR|RELATED|UNIQUE", "similarityScore": 0.0-1.0, "explanation": "Explanation text" }

            Target Text: "%s"
            Existing Context: "%s"
            """;
}
