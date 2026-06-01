---
name: init-spec
description: Initialize markdown Specification files for Java projects, especially DDD-based systems, so they can be used later as context for Spec-Driven Development (SDD). Use when Codex needs to inspect an existing project, identify its architecture, domains, and reusable tools, then create the first batch of files under `./specification`, including constitution.md, architecture.md, DDD.md, domain/*.md, and tool.md.
---

# Init Spec

Initialize the project's Specification only. Do not implement business code as part of this skill.

Use this skill to inspect the current repository, derive stable project facts, and create the initial markdown files that later SDD work will rely on.

## Workflow

1. Use `./specification` as the fixed Specification root directory.

2. Inspect the project before writing any Specification file.
   For Java projects, check at least:
   - `pom.xml`, `build.gradle*`, `settings.gradle*`
   - multi-module folders
   - `src/main/java`
   - package and module names
   - `.gitmodules` if it exists
   - existing docs that define coding or architecture constraints

3. Detect whether the project uses DDD or has strong DDD characteristics.
   Treat DDD as present when the repository clearly shows one or more of these signals:
   - explicit layers such as `domain`, `application`, `infrastructure`, `interfaces`
   - modules mapped to DDD layers
   - bounded contexts or domain packages with clear business separation
   - concepts such as aggregate roots, entities, value objects, repositories, domain services

4. Identify concrete domains.
   Count only domains that are clearly visible from the codebase or existing documents.
   Prefer bounded contexts or top-level business areas over technical packages.
   If the evidence is weak, mark the domain as `待确认` in the generated documents instead of inventing detail.

5. Classify project size from the domain count.
   - More than 3 domains: large project
   - 3 domains or fewer: small project

6. Ask the user to choose the initialization mode after classification unless the user already specified it.
   Use this recommendation:
   - large project: recommend step-by-step initialization
   - small project: recommend all-at-once initialization

7. Generate Specification files under `./specification`.
   Follow the file map and templates in [references/spec-templates.md](references/spec-templates.md).

8. If the user chooses step-by-step initialization, create and maintain `./specification/TODO.md`.
   Update the checklist after each generated Specification file so future runs can continue from the current state.

## Specification Metadata

Every generated Specification markdown file except `TODO.md` must start with YAML frontmatter.

Use the same lightweight pattern as Agent Skill metadata:

- `name`: a short stable identifier for this Specification file
- `description`: one concise sentence that explains both:
  - what this Specification file contains
  - when it should be loaded into AI context during later SDD work

Metadata rules:
- keep the frontmatter short
- use only `name` and `description`
- make `description` routing-friendly rather than verbose
- prefer repository terminology over generic wording
- do not add extra fields unless the user explicitly asks for them

## Required Interaction

Ask this question when the user did not already answer it:

1. After inspection: `当前识别到 <N> 个领域，判定为<大型/小型>项目。建议<分步骤/一次性>初始化。是否按这个方式继续？`

If the user already gave the mode, do not ask again. Respect the explicit choice.

## File Map

Generate files according to the following rules.

### `./specification/constitution/constitution.md`

Always generate this file.

Write a short constitution that captures rules AI must always follow during development in this repository.

Requirements:
- keep it concise
- prefer 10 rules or fewer
- include YAML frontmatter metadata
- derive rules from the real project, not generic best practices
- write only rules with stable, high-confidence evidence
- if an important rule is likely but not fully proven, place it in a short `待确认` section instead of presenting it as a hard rule

### `./specification/architecture/architecture.md`

Always generate this file.

Cover:
- project framework and core runtime stack
- overall layering or module split
- if DDD is used, map modules or packages to DDD layers
- Git submodules, if `.gitmodules` exists
- include YAML frontmatter metadata

Do not turn this file into a feature flow or sequence diagram. Keep it architectural.

### `./specification/architecture/DDD.md`

Generate this file only when DDD is present or strongly implied.

Cover:
- DDD rules that must be followed in this project
- current DDD implementation status
- current layers, bounded contexts, aggregates, entities, value objects, repositories, and domain services when they are visible from the codebase
- include YAML frontmatter metadata

### `./specification/domain/${specific-domain}.md`

Generate one file per concrete domain only when DDD is present or the project clearly has DDD-like domain boundaries.

Requirements:
- replace `${specific-domain}` with a real domain name such as `merchant`, `terminal`, or another domain found in the codebase
- create one file per domain
- describe only that domain, not the whole system
- derive names from the repository instead of inventing them
- include YAML frontmatter metadata

### `./specification/tool/tool.md`

Always generate this file.

Describe reusable utilities or helper methods that are repeatedly used to solve recurring problems.

Focus on utilities such as:
- internal utility classes or helper packages
- framework wrappers used repeatedly by the team
- third-party libraries that have become standard project tools

For each tool, describe:
- what problem it solves
- where it is commonly used
- when it should be preferred

Only include tools with repeated or recommended usage. Do not list one-off helpers.
Include YAML frontmatter metadata.

## Step-by-Step Mode

When the user chooses step-by-step initialization:

1. Create `./specification/TODO.md` before generating the first Specification file.
2. List every planned file as a checklist item.
3. Mark optional files only when they are actually applicable.
4. Update the checklist after each generated file.
5. Re-read `TODO.md` on later runs and continue from the remaining items.

Use this default order unless the user asks for another order:
1. `constitution/constitution.md`
2. `architecture/architecture.md`
3. `architecture/DDD.md` when applicable
4. `domain/*.md`
5. `tool/tool.md`

## Writing Rules

- Prefer facts from the repository over assumptions.
- Prefer short, maintainable markdown over exhaustive prose.
- Use project terminology exactly as it appears in code or docs.
- Mark uncertainty explicitly with `待确认`.
- Avoid copying large code snippets into Specification files.
- Make frontmatter descriptions concise and useful for progressive disclosure.
- Keep `constitution.md` especially short and stable.
- Keep `architecture.md` focused on structure, not behavior.
- Keep `tool.md` focused on recurring usage patterns, not every dependency in the build file.

## Discovery Hints

Use practical repository evidence to build the initial Specification set:

- Frameworks and runtime: inspect Maven or Gradle build files
- Layering: inspect module names, package names, and adapter or infra folders
- Domains: inspect domain-layer subfolders, bounded-context modules, aggregate packages, or business-centric namespaces
- Git submodules: inspect `.gitmodules`
- Reusable tools: inspect common utility packages and repeated imports or call sites

If the repository is mixed or transitional, record the current implementation truthfully instead of forcing an ideal DDD interpretation.

## Completion

Finish only after:
- `./specification` is used as the Specification root
- project size is classified
- the user has chosen step-by-step or all-at-once mode, or already provided it
- all applicable Specification files for the chosen mode are generated
- `TODO.md` is created and updated when step-by-step mode is used
